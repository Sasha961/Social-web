package com.example.service;

import com.example.dto.*;
import com.example.model.Comment;
import com.example.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PostService {

    Page<Post> postsGet (UUID id, Boolean isDeleted, List<UUID> ids, List<Long> accountIds, List<UUID> blockedIds,
                         String author, Boolean withFriends, List<String> tags, LocalDateTime dateFrom,
                         LocalDateTime dateTo, Integer page, Integer size, String sort);

    void postEdit (PostDto postDto);

    void postCreate (HttpEntity<String> httpEntity, String token) throws JsonProcessingException;

    void commentEdit (CommentDto commentDto, UUID id);

    void createCommentPost (HttpEntity<String> httpEntity, String token, UUID id) throws JsonProcessingException;

    void createSubComment (HttpEntity<String> httpEntity, String token, UUID id, UUID commentId) throws JsonProcessingException;

    void deleteComment (UUID id, UUID commentId);

    void delayedPost (PostDto postDto);

    void createLikePost (LikeDto likeDto, UUID id, String token) throws JsonProcessingException;

    void deleteLikePost (UUID id);

    void createLikeComment (UUID id, UUID commentId);

    void deleteLikeComment (UUID id, UUID commentId);

    Page<Comment> commentGet (UUID postID, UUID id, Boolean isDeleted, CommentTypeEnum commentType,
                              Long authorId, UUID parentId, UUID postId,
                              Integer page, Integer size, String sort);

    Page<Comment> subCommentGet (UUID postID, UUID commentId, UUID id, Boolean isDeleted,
                                 CommentTypeEnum commentType,
                                 Long authorId, UUID parentId,
                                 Integer page, Integer size, String sort);

    PostDto postGet (UUID id);

    void deletePost (UUID id);

    ResponseEntity<Void> blockPost(Long id);

    List<PostDto> searchPosts(String searchWord, String offset, String limit);

    ResponseEntity<Void> blockComment(Long id);
    List<CommentDto> searchComment(String searchWord, String offset, String limit);

    String uploadImageToServer(String bearerToken, MultipartFile file);
}
