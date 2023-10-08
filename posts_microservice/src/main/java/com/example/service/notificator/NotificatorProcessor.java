package com.example.service.notificator;

import com.example.dto.CommentDto;
import com.example.dto.LikeDto;
import com.example.dto.notify.ENotificationType;
import com.example.dto.notify.EServiceName;
import com.example.dto.notify.NotificationCommonDto;
import com.example.kafka.KafkaProducer;
import com.example.model.Comment;
import com.example.model.Like;
import com.example.model.Post;
import com.example.repository.CommentRepository;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Artem Lebedev | 19/09/2023 - 00:16
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificatorProcessor {
	private final KafkaProducer kafkaProducer;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;


	/**
	 * @param post <p>
	 *             private UUID id;<p>
	 *             private Long authorId; - ID автора поста<p>
	 *             private String title;  - Заголовок поста<p>
	 */
	public void postNotificator(@NotNull Post post) {
		NotificationCommonDto postNotify = NotificationCommonDto.builder()
				.producerId(post.getAuthorId())
				.consumerId(null)
				.notificationType(ENotificationType.POST)
				.content(post.getTitle())
				.eventId(post.getId())
				.timestamp(new Timestamp(System.currentTimeMillis()))
				.service(EServiceName.POSTS)
				.build();
		log.info(" * POST notification created and ready to send");
		kafkaProducer.produceKafkaMessage("notify-topic-common", postNotify);
	}

	/**
	 * @param comment <p>
	 *                UUID id;<p>
	 *                Long authorId; - ID автора комментария<p>
	 *                String commentText; - Текст комментария<p>
	 *                UUID postId; - ID поста, к которому относится комментарий<p>
	 */
	public void commentNotificator(@NotNull CommentDto comment) {
		Optional<Post> post = postRepository.findById(comment.getPostId());
		Long postAuthorId;
		if (post.isEmpty()) {
			log.error(" ! POST with id {} not exists", comment.getPostId());
			return;
		} else {
			postAuthorId = post.get().getAuthorId();
		}
		NotificationCommonDto commentNotify = NotificationCommonDto.builder()
				.service(EServiceName.POSTS)
				.producerId(comment.getAuthorId())
				.notificationType(ENotificationType.POST_COMMENT)
				.timestamp(new Timestamp(System.currentTimeMillis()))
				.eventId(comment.getId())
				.content(comment.getCommentText())
				.consumerId(postAuthorId)
				.build();
		log.info(" * COMMENT notification created and ready to send");
		kafkaProducer.produceKafkaMessage("notify-topic-common", commentNotify);
	}

	/**
	 * @param like UUID id;
	 *             Long authorId; - ID автора комментария
	 *             UUID itemId; - ID поста или комментария, к которому принадлежит лайк
	 */
	public void likeNotificator(@NotNull LikeDto like) {
		Long consumerId;
		Optional<Comment> commentModel = commentRepository.findById(like.getItemId());
		if (commentModel.isPresent()) {
			consumerId = commentModel.get().getAuthorId();
		} else {
			Optional<Post> postModel = postRepository.findById(like.getItemId());
			if (postModel.isPresent()) {
				consumerId = postModel.get().getAuthorId();
			} else {
				log.error(" ! Unable to retrieve authorId from POST or COMMENT");
				return;
			}
		}
		NotificationCommonDto likeNotify = NotificationCommonDto.builder()
				.producerId(like.getAuthorId())
				.consumerId(consumerId)
				.notificationType(ENotificationType.LIKE)
				.timestamp(new Timestamp(System.currentTimeMillis()))
				.eventId(like.getId())
				.service(EServiceName.POSTS)
				.build();
		log.info(" * LIKE notification created and ready to send");
		kafkaProducer.produceKafkaMessage("notify-topic-common", likeNotify);
	}

}
