package com.example.web.controllers;


import com.example.web.dto.*;
import com.example.web.model.Comment;
import com.example.web.model.Post;
import com.example.web.repository.CommentRepository;
import com.example.web.repository.PostRepository;
import com.example.web.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    //Получение постов
    @GetMapping(value = "/api/v1/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postsGet(PostSearchDto postSearch, Pageable pageable) {
        return postService.postsGet(postSearch, pageable);
    }

    //Редактирование постов
    @PutMapping(value = "/api/v1/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> postEdit() {
        return postService.postEdit();
    }

    //Создание поста
    @PostMapping(value = "/api/v1/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> postCreate() {
        return postService.postCreate();
    }

    //Редактирование комментария
    @PutMapping(value = "/api/v1/post/{id}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> commentEdit(String id) {
        return postService.commentEdit(id);
    }

    //Создание комментария к посту
    @PostMapping(value = "/api/v1/post/{id}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> createCommentPost(String id) {
        return postService.createCommentPost(id);
    }

    //Создание сабкомментария
    @PutMapping(value = "/api/v1/post/{id}/comment/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> createSubComment(String id, String commentId) {
        return postService.createSubComment(id, commentId);
    }

    //Удаление комментария
    @DeleteMapping (value = "/api/v1/post/{id}/comment/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteComment(String id, String commentId) {
        return postService.deleteComment(id, commentId);
    }

    //Отложенный пост
    @PutMapping (value = "/api/v1/post/delayed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delayedPost() {
        return postService.delayedPost();
    }

    //Создание лайка типа POST
    @PostMapping(value = "/api/v1/post/{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LikeDto> createLikePost(String id) {
        return postService.createLikePost(id);
    }

    //Удаление лайка типа POST
    @DeleteMapping (value = "/api/v1/post/{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteLikePost(String id) {
        return postService.deleteLikePost(id);
    }

    //Создание лайка типа COMMENT
    @PostMapping(value = "/api/v1/post/{id}/comment/{commentId}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createLikeComment(String id, String commentId) {
        return postService.createLikeComment(id, commentId);
    }

    //Удаление лайка типа COMMENT
    @DeleteMapping (value = "/api/v1/post/{id}/comment/{commentId}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteLikeComment(String id, String commentId) {
        return postService.deleteLikeComment(id, commentId);
    }

    //Получение комментариев
    @GetMapping(value = "/api/v1/post/{postId}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> commentGet(String postID, CommentSearchDto commentSearch, Pageable pageable) {
        return postService.commentGet(postID,  commentSearch,  pageable);
    }

    //Получение сабкомментариев
    @GetMapping(value = "/api/v1/post/{postId}/comment/{commentId}/subcomment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> subCommentGet(String postID, String commentId, CommentSearchDto commentSearch, Pageable pageable) {
        return postService.subCommentGet(postID, commentId, commentSearch, pageable);
    }

    //Получение поста
    @GetMapping(value = "/api/v1/post/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postGet(String id) {
        return postService.postGet(id);
    }

    //Удаление поста
    @DeleteMapping (value = "/api/v1/post/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletePost(String id) {
        return postService.deletePost(id);
    }

    // блокировка/разблокировка поста
    @PutMapping(value = "/api/v1/is-post")
    public ResponseEntity<Void> blockPost(@RequestParam(value = "id") Long id) {
        return postService.blockPost(id);
    }

    //Поиск постов
    @GetMapping(value = "/api/v1/post/search-posts/{searchWord}")
    public List<Post> searchPosts(@PathVariable("searchWord") String searchWord){
        return postService.searchPosts(searchWord);
    }

    //всего постов
    @GetMapping(value = "/api/v1/post/count")
    public long allCountPosts(){
        return postRepository.count();
    }

    // блокировка/разблокировка комментариев
    @PutMapping(value = "/api/v1/is-comment")
    public ResponseEntity<Void> blockComment(@RequestParam(value = "id") Long id) {
        return postService.blockComment(id);
    }

    //Поиск комментариев
    @GetMapping(value = "/api/v1/post/search-comment/{searchWord}")
    public List<Comment> searchComment(@PathVariable("searchWord") String searchWord){
        return postService.searchComment(searchWord);
    }

    //всего комментариев
    @GetMapping(value = "/api/v1/comment/count")
    public long allCountComment(){
        return commentRepository.count();
    }
}
