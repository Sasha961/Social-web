package com.example.web.service;

import com.example.web.dto.*;
import com.example.web.model.Comment;
import com.example.web.model.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    ResponseEntity<Void> postsGet (PostSearchDto postSearch, Pageable pageable);

    ResponseEntity<PostDto> postEdit ();

    ResponseEntity<PostDto> postCreate ();

    ResponseEntity<CommentDto> commentEdit (String id);

    ResponseEntity<CommentDto> createCommentPost (String id);

    ResponseEntity<CommentDto> createSubComment (String id, String commentId);

    ResponseEntity<Void> deleteComment (String id, String commentId);

    ResponseEntity<Void> delayedPost ();

    ResponseEntity<LikeDto> createLikePost (String id);

    ResponseEntity<Void> deleteLikePost (String id);

    ResponseEntity<Void> createLikeComment (String id, String commentId);

    ResponseEntity<Void> deleteLikeComment (String id, String commentId);

    ResponseEntity<Void> commentGet (String postID, CommentSearchDto commentSearch, Pageable pageable);

    ResponseEntity<Void> subCommentGet (String postID, String commentId, CommentSearchDto commentSearch, Pageable pageable);

    ResponseEntity<Void> postGet (String id);

    ResponseEntity<Void> deletePost (String id);

    ResponseEntity<Void> blockPost(Long id);

    List<Post> searchPosts(String searchWord);

    ResponseEntity<Void> blockComment(Long id);
    List<Comment> searchComment(String searchWord);
}
