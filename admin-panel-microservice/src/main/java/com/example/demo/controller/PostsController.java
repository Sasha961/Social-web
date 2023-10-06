package com.example.demo.controller;


import com.example.demo.dto.post.Post;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface PostsController {

    @GetMapping(value = "/search-posts/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Поиск постов")
    List<Post> searchPosts(@PathVariable(value = "word") String word);

    @PutMapping(value = "/block-post", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Блокировка поста")
    void blockPost(@RequestParam(value = "id") Long id);

    @PutMapping(value = "/unblock-post", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Разблокировка поста")
    void unblockPost(@RequestParam(value = "id") Long id);

}
