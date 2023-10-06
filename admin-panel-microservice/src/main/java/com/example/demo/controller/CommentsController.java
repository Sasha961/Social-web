package com.example.demo.controller;

import com.example.demo.dto.comment.Comment;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CommentsController {

    @GetMapping(value = "/search-comments/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Поиск комментариев")
    List<Comment> searchComment(@PathVariable(value = "word", required = false) String word);

    @PutMapping(value = "/block-comments", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Блокировка комментария")
    void blockComment(@RequestParam(value = "id") Long id);

    @PutMapping(value = "/unblock-comments", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Разблокировка комментария")
    void unblockComment(@RequestParam(value = "id") Long id);
}
