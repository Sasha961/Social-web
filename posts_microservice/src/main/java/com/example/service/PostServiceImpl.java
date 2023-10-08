package com.example.service;

import com.example.client.AdminClient;
import com.example.dto.*;
import com.example.exception.ResourceNotFoundException;
import com.example.kafka.KafkaProducer;
import com.example.model.*;
import com.example.repository.CommentRepository;
import com.example.repository.DelayedPostRepository;
import com.example.repository.LikeRepository;
import com.example.repository.PostRepository;
import com.example.service.notificator.NotificatorProcessor;
import com.example.service.searchers.GetCommentSearcher;
import com.example.service.searchers.GetPostsSearcher;
import com.example.service.searchers.GetSubCommentSearcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final LikeRepository likeRepository;
	private final DelayedPostRepository delayedPostRepository;
	private final AdminClient adminClient;

	@Autowired
	private ModelMapper modelMapper;
	private final KafkaProducer kafkaProducer;
	private final NotificatorProcessor notificatorProcessor;
	private final ObjectMapper objectMapper;

	@Override
	public Page<Post> postsGet(UUID id, Boolean isDeleted, List<UUID> ids, List<Long> accountIds, List<UUID> blockedIds,
							   String author, Boolean withFriends, List<String> tags, LocalDateTime dateFrom,
							   LocalDateTime dateTo, Integer page, Integer size, String sort) {

		if (page == null || page < 0) {
			page = 0;
		}
		if (size == null) {
			size = 10;
		}

		GetPostsSearcher getPostsSearcher = new GetPostsSearcher();
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "publishDate");

		return postRepository.findAll(getPostsSearcher.postsSearch(id, isDeleted, ids, accountIds, blockedIds,
				author, withFriends, tags, dateFrom, dateTo), pageable);
	}

	@Override
	public void postEdit(PostDto postDto) {
		Post post = modelMapper.map(postDto, Post.class);
		post.setTimeChanged(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime());
		postRepository.save(post);
	}

	@Override
	@Transactional
	public void postCreate(HttpEntity<String> httpEntity, String token) throws JsonProcessingException {

		//log.info("Разница во времени   " + Duration.between(localDateTime, LocalDateTime.now()).toMinutes());
		HashMap parameters = new ObjectMapper().readValue(httpEntity.getBody(), HashMap.class);

		Post post = new Post();
		post.setId(UUID.randomUUID());
		post.setIsDeleted(false);
		post.setTimeCreated(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime());
		post.setTimeChanged(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime());

		if (parameters.containsKey("authorId")) {
			Long authorId = Long.valueOf(String.valueOf(parameters.get("authorId")));
			post.setAuthorId(authorId);
		} else {
			post.setAuthorId(idFromToken(token));
		}

		if (parameters.containsKey("title")) {
			post.setTitle((String) parameters.get("title"));
		}

		post.setPostType(TypePostEnum.POSTED);

		if (parameters.containsKey("postText")) {
			post.setPostText((String) parameters.get("postText"));
		}

		post.setIsBlocked(false);

		if (parameters.containsKey("commentsCount")) {
			post.setCommentsCount((Integer) parameters.get("commentsCount"));
		} else {
			post.setCommentsCount(0);
		}

		//Теги поста
		//private Tag tags;
		//private String tags;

		//Список типов реакций
		//private Reaction reactions;
		//private String reactions;


		if (parameters.containsKey("myReaction")) {
			post.setMyReaction((String) parameters.get("myReaction"));
		}

		if (parameters.containsKey("likeAmount")) {
			post.setLikeAmount((Integer) parameters.get("likeAmount"));
		} else {
			post.setLikeAmount(0);
		}

		if (parameters.containsKey("myLike")) {
			post.setMyLike((Boolean) parameters.get("myLike"));
		} else {
			post.setMyLike(false);
		}

		if (parameters.containsKey("imagePath")) {
			post.setImagePath((String) parameters.get("imagePath"));
		}

		post.setPublishDate(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime());

		postRepository.save(post);
		try {
			notificatorProcessor.postNotificator(post);
		} catch (RuntimeException e) {
			log.error(" ! Unable to send notification");
		}

	}

	@Override
	public void commentEdit(CommentDto commentDto, UUID id) {
		// что делать с айдишником, непонятно, в dto есть свой
		Comment comment = modelMapper.map(commentDto, Comment.class);
		commentRepository.save(comment);
	}

	@Override
	public void createCommentPost(HttpEntity<String> httpEntity, String token, UUID id) throws JsonProcessingException {
		// тот же вопрос
		//Comment comment = modelMapper.map(commentDto, Comment.class);

		HashMap parameters = new ObjectMapper().readValue(httpEntity.getBody(), HashMap.class);

		Comment comment = new Comment();

		comment.setId(UUID.randomUUID());
		comment.setIsDeleted(false);
		comment.setCommentType(CommentTypeEnum.POST);   //    Тип комментария: POST - к посту, COMMENT - к комментарию, субкомментарий
		comment.setTimeCreated(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime()); // Время создания комментария
		comment.setTimeChanged(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime()); // Время изменения комментария
		comment.setAuthorId(idFromToken(token)); // ID автора комментария
		comment.setParentId(null);     // ID родителя, к которому был оставлен комментарий
		if (parameters.containsKey("commentText")) {
			comment.setCommentText((String) parameters.get("commentText"));  //Текст комментария
		}
		comment.setPostId(id);     //ID поста, к которому относится комментарий
		comment.setIsBlocked(false);    //Заблокирован ли?
		comment.setLikeAmount(0L);      //Количество лайков комментария
		comment.setMyLike(false);        // Это твой лайк?
		comment.setCommentsCount(0L);   //Количество комментариев
		if (parameters.containsKey("imagePath")) {
			comment.setImagePath((String) parameters.get("imagePath"));//Путь к изображению
		}

		commentRepository.save(comment);


		// С Артемом проговорить, так как дтошка ушла

//		try {
//			notificatorProcessor.commentNotificator(commentDto);
//		} catch (RuntimeException e) {
//			log.error(" ! Unable to send notification");
//		}

	}

	@Override
	public void createSubComment(HttpEntity<String> httpEntity, String token, UUID id, UUID commentId) throws JsonProcessingException {
		//Comment comment = modelMapper.map(commentDto, Comment.class);
		HashMap parameters = new ObjectMapper().readValue(httpEntity.getBody(), HashMap.class);

		Comment comment = new Comment();

		comment.setId(UUID.randomUUID());
		comment.setIsDeleted(false);
		comment.setCommentType(CommentTypeEnum.COMMENT);   //    Тип комментария: POST - к посту, COMMENT - к комментарию, субкомментарий
		comment.setTimeCreated(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime()); // Время создания комментария
		comment.setTimeChanged(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDateTime()); // Время изменения комментария
		comment.setAuthorId(idFromToken(token)); // ID автора комментария
		comment.setParentId(commentId);     // ID родителя, к которому был оставлен комментарий
		if (parameters.containsKey("commentText")) {
			comment.setCommentText((String) parameters.get("commentText"));  //Текст комментария
		}
		comment.setPostId(id);     //ID поста, к которому относится комментарий
		comment.setIsBlocked(false);    //Заблокирован ли?
		comment.setLikeAmount(0L);      //Количество лайков комментария
		comment.setMyLike(false);        // Это твой лайк?
		comment.setCommentsCount(0L);   //Количество комментариев
		if (parameters.containsKey("imagePath")) {
			comment.setImagePath((String) parameters.get("imagePath"));//Путь к изображению
		}

		commentRepository.save(comment);
	}

	@Override
	@Transactional
	public void deleteComment(UUID id, UUID commentId) {
		//зачем сюда приходят два айдишника?????
		Optional<Comment> comment = commentRepository.findById(commentId);
		if (comment.isEmpty()) {
			//обработать хендлером
			// return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		commentRepository.deleteById(commentId);
	}

	@Override
	public void delayedPost(PostDto postDto) {
		DelayedPost delayedPost = modelMapper.map(postDto, DelayedPost.class);
		delayedPostRepository.save(delayedPost);
		//что приходит не понятно , пока будет postDto
	}

	@Override
	public void createLikePost(LikeDto likeDto, UUID id, String token) throws JsonProcessingException {
		Like like = new Like(); //modelMapper.map(likeDto, Like.class);

		like.setId(UUID.randomUUID());
		like.setIsDeleted(false);
		like.setAuthorId(idFromToken(token)); //получить из токена и переписать
		like.setTimeCreated(LocalDateTime.now());
		like.setItemId(id);
		like.setLikeType(CommentTypeEnum.POST);
		like.setReactionType("LOL"); // требует уточнения
		Optional<Post> postOptional = postRepository.findById(id);
		if (postOptional.isEmpty()) {
			//обработать хендлером
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Post post = postRepository.getById(id);
			if (post.getLikeAmount() == null) {
				post.setLikeAmount(1);
			} else {
				post.setLikeAmount(post.getLikeAmount() + 1);
			}
			post.setMyLike(true);
			postRepository.save(post);
		}

		likeRepository.save(like);

		try {
			notificatorProcessor.likeNotificator(likeDto);
		} catch (RuntimeException e) {
			log.error(" ! Unable to send notification");
		}


	}

	@Override
	@Transactional
	public void deleteLikePost(UUID id) {
		Optional<Like> like = likeRepository.findById(id);
		if (like.isEmpty()) {
			//обработать хендлером
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		UUID idPost = like.get().getItemId();

		Optional<Post> postOptional = postRepository.findById(idPost);
		if (postOptional.isEmpty()) {
			//обработать хендлером
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Post post = postRepository.getById(idPost);
			if (post.getLikeAmount() != 0) {
				post.setLikeAmount(post.getLikeAmount() - 1);
			}
			post.setMyLike(false);
			postRepository.save(post);
		}
		likeRepository.deleteById(id);
	}

	@Override
	public void createLikeComment(UUID id, UUID commentId) {
		//уточнить где взять остальные параметры
		//Like like = modelMapper.map(likeDto, Like.class);
		//likeRepository.save(like);

		Like like = new Like(); //modelMapper.map(likeDto, Like.class);

		like.setId(UUID.randomUUID());
		like.setIsDeleted(false);
		Optional<Comment> comment = commentRepository.findById(commentId);
		if (comment.isEmpty()) {
			//обработать хендлером
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		like.setAuthorId(commentRepository.findById(commentId).get().getAuthorId());
		like.setTimeCreated(LocalDateTime.now());
		like.setItemId(commentId);
		like.setLikeType(CommentTypeEnum.COMMENT);
		like.setReactionType("LOL"); // требует уточнения
		likeRepository.save(like);

	}

	@Override
	@Transactional
	public void deleteLikeComment(UUID id, UUID commentId) {
		Optional<Like> like = likeRepository.findById(id);
		if (like.isEmpty()) {
			//обработать хендлером
			// return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		likeRepository.deleteById(id);
	}

	@Override
	public Page<Comment> commentGet(UUID postID, UUID id, Boolean isDeleted, CommentTypeEnum commentType,
	                                Long authorId, UUID parentId, UUID postId,
	                                Integer page, Integer size, String sort) {
		GetCommentSearcher getCommentSearcher = new GetCommentSearcher();
		if (page == null || page < 0) {
			page = 0;
		}
		if (size == null) {
			size = 10;
		}

		Pageable pageable = PageRequest.of(page, size);//, Sort.Direction.DESC, "time_changed");

		return commentRepository.findAll(getCommentSearcher.commentSearch(postID, id, isDeleted,
				commentType, authorId, parentId, postId), pageable);


	}

	@Override
	public Page<Comment> subCommentGet(UUID postID, UUID commentId, UUID id, Boolean isDeleted,
									   CommentTypeEnum commentType,
									   Long authorId, UUID parentId,
									   Integer page, Integer size, String sort) {
		GetSubCommentSearcher getSubCommentSearcher = new GetSubCommentSearcher();
		Pageable pageable = PageRequest.of(page, 10);
		return commentRepository.findAll(getSubCommentSearcher.subCommentSearch(postID, commentId, id, isDeleted,
				commentType, authorId, parentId), pageable);

	}

	@Override
	public PostDto postGet(UUID id) {
		Optional<Post> post = postRepository.findById(id);
		if (post.isEmpty()) {
			//обработать хендлером
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Post postToDto = postRepository.getById(id);
		return modelMapper.map(postToDto, PostDto.class);
	}

	@Override
	@Transactional
	public void deletePost(UUID id) {

		Optional<Post> post = postRepository.findById(id);
		if (post.isEmpty()) {
			//обработать хендлером
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		postRepository.deleteById(id);
	}

	@Override
	public ResponseEntity<Void> blockPost(Long id) {

		Optional<Post> post = postRepository.findById(id);
		if (post.isEmpty()) {
			//обработать хендлером
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		post.get().setIsBlocked(!post.get().getIsBlocked());
		postRepository.save(post.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public List<PostDto> searchPosts(String searchWord, String offset, String limit) {

		if (searchWord.isEmpty()) {
			throw new ResourceNotFoundException("пусто");
		}

		String[] wordsList = searchWord.split(" ");
		List<PostDto> postDtoList = new ArrayList<>();
		int limitToInt = Integer.parseInt(limit);

		for (String value : wordsList) {
			Specification<Post> search = Specification
					.where(PostsSpecification.findByPostTitle(value))
					.or(PostsSpecification.findByPostText(value));

			postDtoList.addAll(postRepository.findAll(search, PageRequest.of(Integer.parseInt(offset), limitToInt))
					.stream()
					.map(post -> objectMapper.convertValue(post, PostDto.class))
					.collect(Collectors.toList()));
			if (postDtoList.size() > limitToInt) {
				break;
			}
		}
		return postDtoList;
	}

	@Override
	public ResponseEntity<Void> blockComment(Long id) {

		Optional<Comment> comment = commentRepository.findById(id);
		if (comment.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		comment.get().setIsBlocked(!comment.get().getIsBlocked());
		commentRepository.save(comment.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public List<CommentDto> searchComment(String searchWord, String offset, String limit) {

		if (searchWord.isEmpty()) {
			throw new ResourceNotFoundException("пусто");
		}

		String[] wordsList = searchWord.split(" ");
		List<CommentDto> commentDtoList = new ArrayList<>();
		int limitToInt = Integer.parseInt(limit);

		for (String value : wordsList) {
			Specification<Comment> search = Specification
					.where(PostsSpecification.findByComment(value));

			commentDtoList.addAll(commentRepository.findAll(search, PageRequest.of(Integer.parseInt(offset), limitToInt))
					.stream()
					.map(post -> objectMapper.convertValue(post, CommentDto.class))
					.collect(Collectors.toList()));
			if (commentDtoList.size() > limitToInt) {
				break;
			}
		}
		return commentDtoList;
	}

	@Transactional
	@Scheduled(fixedRate = 60000)
	public void scannerOfDelayedPosts() {
		Specification<DelayedPost> specification = Specification.where(null);
		specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("publishDate"), LocalDateTime.now())));

		if (specification != null) {
			List<DelayedPost> delayedPosts = delayedPostRepository.findAll(specification);
			for (DelayedPost delayedPost : delayedPosts) {
				Post post = modelMapper.map(delayedPost, Post.class);
				postRepository.save(post);
				log.info("Опубликован отложенный пост c id  " + post.getId());
				delayedPostRepository.deleteById(delayedPost.getId());
			}
		}
	}

	public Long idFromToken(String token) throws JsonProcessingException {
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String[] chunks = token.split("\\.");
		String json = new String(decoder.decode(chunks[1]));
		ObjectMapper mapper = new ObjectMapper();
		Map jsonMap = mapper.readValue(json, Map.class);
		return ((Number) jsonMap.get("userId")).longValue();
	}

	@Override
	public String uploadImageToServer(String bearerToken, MultipartFile file) {
		return adminClient.getLinkForUploadImage(bearerToken, file).getFileName();
	}
}
