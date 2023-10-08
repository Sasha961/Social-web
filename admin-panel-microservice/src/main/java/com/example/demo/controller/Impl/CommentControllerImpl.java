package com.example.demo.controller.Impl;


import com.example.demo.controller.CommentsController;
import com.example.demo.controller.feignClient.PostsControllerFeign;
import com.example.demo.dto.comment.Comment;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
@SecurityRequirement(name = "JWT")
@Tag(name = "Comments service", description = "Работа с комментариями")
public class CommentControllerImpl  implements CommentsController {

    private final PostsControllerFeign postsControllerFeign;

    @Override
    public List<Comment> searchComment(String word) {
        return postsControllerFeign.searchComment(word);
    }

    @Override
    public void blockComment(Long id) {
        postsControllerFeign.blockComment(id);
    }

    @Override
    public void unblockComment(Long id) {
        postsControllerFeign.blockComment(id);
    }
}
