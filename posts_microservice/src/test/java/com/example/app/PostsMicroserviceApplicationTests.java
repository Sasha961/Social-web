package com.example.app;

import com.example.dto.PostDto;
import com.example.dto.ReactionDto;
import com.example.dto.TagDto;
import com.example.dto.TypePostEnum;
import com.example.model.Post;
import com.example.repository.PostRepository;
import com.example.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

//@SpringBootTest

//@RunWith(SpringRunner.class)
class PostsMicroserviceApplicationTests {

	//@ClassRule
	//public static PostgreSQLContainer<BuildingPostgresqlContainer> postgreSQLContainer = BuildingPostgresqlContainer.getInstance();

	//@Autowired
	private PostRepository postRepository;

	//@Autowired
	private ModelMapper modelMapper;

	PostDto[] postDtos = new PostDto[10];

	//@Before
	public void setUp()	{

		for (int i = 0; i < 10; i++){
			postDtos[i] = createRandomPost();
		}

		for (int i = 0; i < 9; i++) {
			Post post = modelMapper.map(postDtos[i], Post.class);
			postRepository.save(post);
		}
	}




	private final PostServiceImpl postService;

	//@Autowired
	PostsMicroserviceApplicationTests(PostServiceImpl postService) {
		this.postService = postService;
	}


	//@Test
//	public void postCreatedTest() {
//
//		postService.postCreate(postDtos[9]);
//		Post postTest = modelMapper.map(postDtos[9], Post.class);
//		Assertions.assertEquals(postRepository.findById(postDtos[9].getId()), postTest);
//
//	}
	//@Test
//	public void postEditTest() {
//
//
//		postService.postCreate(postDtos[9]);
//		Assertions.assertEquals(postRepository.findById(postDtos[9].getId()), postDtos[9]);
//
//
//	}
	//@Test
	//public void deletePostTest(){
	//	postService.deletePost(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
	//}

	public PostDto createRandomPost(){

//		ReactionDto reactionDto = ReactionDto.builder()
//				.count(3)
//				.reactionType("гы гы")
//				.build();
//		TagDto tagDto = TagDto.builder()
//				.id(UUID.randomUUID())
//				.isDeleted(true)
//				.name("имя тега")
//				.build();
//		PostDto randomPostDto = PostDto.builder()
//				.id(UUID.randomUUID())
//				.isDeleted(true)
//				.time(LocalDateTime.now())
//				.timeChanged(LocalDateTime.now())
//				.authorId(UUID.randomUUID())
//				.title("TestTitle")
//				.type(TypePostEnum.POSTED)
//				.postText("Чушь какая то")
//				.isBlocked(false).commentsCount(1)
//				.tags(tagDto)
//				.reactions(reactionDto)
//				.myReaction("Reaction is great")
//				.likeAmount(2)
//				.myLike(false)
//				.imagePath("http address")
//				.publishDate(LocalDateTime.now())
//				.build();

        return null; //randomPostDto;
    }
}
