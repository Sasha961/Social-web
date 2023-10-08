package com.example.controllers;

import com.example.dto.*;
import com.example.model.Comment;
import com.example.model.Post;
import com.example.repository.CommentRepository;
import com.example.repository.PostRepository;
import com.example.service.PostServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/post")
@RequiredArgsConstructor

public class PostController {

    @Autowired
    private final PostServiceImpl postService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Operation(description = "Получение постов", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping()
    public Page<Post> postsGet(@RequestParam(name = "id", required = false) UUID id,
                               @RequestParam(name = "isDeleted", required = false) Boolean isDeleted,
                               @RequestParam(name = "ids", required = false) List<UUID> ids,
                               @RequestParam(name = "accountIds", required = false) List<Long> accountIds,
                               @RequestParam(name = "blockedIds", required = false) List<UUID> blockedIds,
                               @RequestParam(name = "author", required = false) String author,
                               @RequestParam(name = "withFriends", required = false) Boolean withFriends,
                               @RequestParam(name = "tags", required = false) List<String> tags,
                               @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                               @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
                               @RequestParam(name = "page", required = false) Integer page,
                               @RequestParam(name = "size", required = false) Integer size,
                               @RequestParam(name = "sort", required = false) String sort) {
        return postService.postsGet(id, isDeleted, ids, accountIds, blockedIds, author,
                withFriends, tags, dateFrom, dateTo, page, size, sort);
    }

    //дублер, из фронта  приходит по другому адресу
    @GetMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Post> postsGet2(@RequestParam(name = "id", required = false) UUID id,
                                @RequestParam(name = "isDeleted", required = false) Boolean isDeleted,
                                @RequestParam(name = "ids", required = false) List<UUID> ids,
                                @RequestParam(name = "accountIds", required = false) List<Long> accountIds,
                                @RequestParam(name = "blockedIds", required = false) List<UUID> blockedIds,
                                @RequestParam(name = "author", required = false) String author,
                                @RequestParam(name = "withFriends", required = false) Boolean withFriends,
                                @RequestParam(name = "tags", required = false) List<String> tags,
                                @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                @RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
                                @RequestParam(name = "page", required = false) Integer page,
                                @RequestParam(name = "size", required = false) Integer size,
                                @RequestParam(name = "sort", required = false) String sort) {
        return postService.postsGet(id, isDeleted, ids, accountIds, blockedIds, author,
                withFriends, tags, dateFrom, dateTo, page, size, sort);
    }

    @Operation(description = "Редактирование постов", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PutMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public void postEdit(@RequestBody PostDto postDto) {
        postService.postEdit(postDto);
    }

    @Operation(description = "Создание поста", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void postCreate(HttpEntity<String> httpEntity,
                           @RequestHeader (name="Authorization") String token) throws JsonProcessingException {
        postService.postCreate(httpEntity, token);
    }

    @Operation(description = "Редактирование комментария", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PutMapping(value = "/{id}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public void commentEdit(@RequestBody CommentDto commentDto, @PathVariable UUID id) {
        postService.commentEdit(commentDto, id);
    }

    @Operation(description = "Создание комментария к посту", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PostMapping(value = "/{id}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createCommentPost(HttpEntity<String> httpEntity,
                                  @RequestHeader (name="Authorization") String token,
                                  @PathVariable UUID id) throws JsonProcessingException {
        postService.createCommentPost(httpEntity, token, id);
    }

    @Operation(description = "Создание сабкомментария", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PutMapping(value = "/{id}/comment/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createSubComment(HttpEntity<String> httpEntity,
                                 @RequestHeader (name="Authorization") String token,
                                 @PathVariable(name = "id") UUID id,
                                 @PathVariable(name = "commentId") UUID commentId) throws JsonProcessingException {
        postService.createSubComment(httpEntity, token, id, commentId);
    }

    @Operation(description = "Удаление комментария", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @DeleteMapping(value = "/{id}/comment/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteComment(@PathVariable UUID id, @PathVariable UUID commentId) {
        postService.deleteComment(id, commentId);
    }

    @Operation(description = "Отложенный пост", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PutMapping(value = "/delayed", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delayedPost(@RequestBody PostDto postDto) {
        postService.delayedPost(postDto);
    }

    @Operation(description = "Создание лайка типа POST", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PostMapping(value = "/{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createLikePost(@RequestBody(required = false) LikeDto likeDto,
                               @PathVariable UUID id,
                               @RequestHeader (name="Authorization") String token) throws JsonProcessingException {
        postService.createLikePost(likeDto, id, token);
    }

    @Operation(description = "Удаление лайка типа POST", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @DeleteMapping(value = "/{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteLikePost(@PathVariable UUID id) {
        postService.deleteLikePost(id);
    }

    @Operation(description = "Создание лайка типа COMMENT", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PostMapping(value = "/{id}/comment/{commentId}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createLikeComment(@PathVariable UUID id, @PathVariable UUID commentId) {
        postService.createLikeComment(id, commentId);
    }

    @Operation(description = "Удаление лайка типа COMMENT", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @DeleteMapping(value = "/{id}/comment/{commentId}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteLikeComment(@PathVariable UUID id, @PathVariable UUID commentId) {
        postService.deleteLikeComment(id, commentId);
    }

    @Operation(description = "Получение комментариев", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping(value = "/{postId}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Comment> commentGet(@PathVariable(name = "postId") UUID postID,
                                    @RequestParam(name = "id", required = false) UUID id,
                                    @RequestParam(name = "isDeleted", required = false) Boolean isDeleted,
                                    @RequestParam(name = "commentType", required = false) CommentTypeEnum commentType,   //'Тип комментария: POST, COMMENT'
                                    @RequestParam(name = "authorId", required = false) Long authorId,                //ID автора комментария
                                    @RequestParam(name = "parentId", required = false) UUID parentId,                 //ID родителя комментария
                                    @RequestParam(name = "postId", required = false) UUID postId,                   //ID поста, к которому относится комментарий
                                    @RequestParam(name = "page", required = false) Integer page,
                                    @RequestParam(name = "size", required = false) Integer size,
                                    @RequestParam(name = "sort", required = false) String sort) {
        return postService.commentGet(postID, id, isDeleted, commentType, authorId, parentId,
                postId, page, size, sort);
    }

    @Operation(description = "Получение сабкомментариев", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping(value = "/{postId}/comment/{commentId}/subcomment", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Comment> subCommentGet(@PathVariable(name = "postId") UUID postID,
                                       @PathVariable(name = "commentId") UUID commentId,
                                       @RequestParam(name = "id", required = false) UUID id,
                                       @RequestParam(name = "isDeleted", required = false) Boolean isDeleted,
                                       @RequestParam(name = "commentType", required = false) CommentTypeEnum commentType,   //'Тип комментария: POST, COMMENT'
                                       @RequestParam(name = "authorId", required = false) Long authorId,                //ID автора комментария
                                       @RequestParam(name = "parentId", required = false) UUID parentId,                 //ID родителя комментария
                                       //@RequestParam(name = "postId", required = false) UUID postId,                   //ID поста, к которому относится комментарий
                                       @RequestParam(name = "page", required = false) Integer page,
                                       @RequestParam(name = "size", required = false) Integer size,
                                       @RequestParam(name = "sort", required = false) String sort) {
        return postService.subCommentGet(postID, commentId, id, isDeleted, commentType, authorId, parentId,
                 page, size, sort);
    }

    @Operation(description = "Получение поста", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto postGet(@PathVariable UUID id) {
        return postService.postGet(id);
    }

    @Operation(description = "Удаление поста", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @DeleteMapping(value = "/post/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deletePost(@PathVariable(name = "id") UUID id) {
        postService.deletePost(id);
    }

    //---------------------------------------------------------------------------------------------

    @Operation(description = "блокировка/разблокировка поста", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PutMapping(value = "/api/v1/is-post")
    public ResponseEntity<Void> blockPost(@RequestParam(value = "id") Long id) {
        return postService.blockPost(id);
    }

    @Operation(description = "Поиск постов по названию и тексту", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping(value = "/search-posts")
    public List<PostDto> searchPosts(@RequestParam(name = "searchWord") String searchWord,
                                     @RequestParam(defaultValue = "0") String offset,
                                     @RequestParam(defaultValue = "5") String limit) {
        return postService.searchPosts(searchWord, offset, limit);
    }

    @Operation(description = "всего постов", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping(value = "/count")
    public long allCountPosts() {
        return postRepository.count();
    }

    @Operation(description = "блокировка/разблокировка комментариев", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PutMapping(value = "/api/v1/is-comment")
    public ResponseEntity<Void> blockComment(@RequestParam(value = "id") Long id) {
        return postService.blockComment(id);
    }

    @Operation(description = "Поиск комментариев по слову в тексте", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping(value = "/search-comment")
    public List<CommentDto> searchComment(@RequestParam(name = "searchWord") String searchWord,
                                          @RequestParam(defaultValue = "0") String offset,
                                          @RequestParam(defaultValue = "5") String limit) {
        return postService.searchComment(searchWord, offset, limit);
    }

    @Operation(description = "всего комментариев", tags = {"Post service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping(value = "/api/v1/comment/count")
    public long allCountComment() {
        return commentRepository.count();
    }

    @Operation(summary = "addPhotoToStorage", description = "Добавление картинки к посту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/addImage",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    String editAccountIfLogin(@RequestHeader("Authorization") @NonNull String bearerToken,
                              @RequestParam("file") MultipartFile file ){
        return postService.uploadImageToServer(bearerToken,file);
    }

}
