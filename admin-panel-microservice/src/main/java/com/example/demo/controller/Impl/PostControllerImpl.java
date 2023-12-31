package com.example.demo.controller.Impl;

import com.example.demo.controller.PostsController;
import com.example.demo.controller.feignClient.PostsControllerFeign;
import com.example.demo.dto.post.Post;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/posts")
@SecurityRequirement(name = "JWT")
@Tag(name = "PostService", description = "Работа с постами")
public class PostControllerImpl implements PostsController {

    private final PostsControllerFeign postsControllerFeign;

    @Override
    public List<Post> searchPosts(String word) {
        return postsControllerFeign.searchPosts(word);
    }

    @Override
    public void blockPost(Long id) {
        postsControllerFeign.blockPost(id);
    }

    @Override
    public void unblockPost(Long id) {
        postsControllerFeign.blockPost(id);
    }
}
