package com.example.web.service;

import com.example.web.model.Comment;
import com.example.web.repository.CommentRepository;
import com.example.web.repository.PostRepository;
import com.example.web.dto.*;
import com.example.web.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Override
    public ResponseEntity<Void> postsGet(PostSearchDto postSearch, Pageable pageable) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostDto> postEdit() {
        return new ResponseEntity<PostDto>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostDto> postCreate() {
        return new ResponseEntity<PostDto>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentDto> commentEdit(String id) {
        return new ResponseEntity<CommentDto>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentDto> createCommentPost(String id) {
        return new ResponseEntity<CommentDto>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentDto> createSubComment(String id, String commentId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteComment(String id, String commentId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delayedPost() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LikeDto> createLikePost(String id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteLikePost(String id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> createLikeComment(String id, String commentId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteLikeComment(String id, String commentId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> commentGet(String postID, CommentSearchDto commentSearch, Pageable pageable) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> subCommentGet(String postID, String commentId, CommentSearchDto commentSearch, Pageable pageable) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> postGet(String id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deletePost(String id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> blockPost(Long id) {

        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        post.get().setBlocked(!post.get().isBlocked());
        postRepository.save(post.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public List<Post> searchPosts(String searchWord) {

        Specification<Post> search = Specification.where(null);
        search.or(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", searchWord))));
        search.or(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("postText"), String.format("%%%s%%", searchWord))));
        return postRepository.findAll(search);
    }

    @Override
    public ResponseEntity<Void> blockComment(Long id) {

        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        comment.get().setBlocked(!comment.get().isBlocked());
        commentRepository.save(comment.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<Comment> searchComment(String searchWord) {
        Specification<Comment> searchComment = Specification.where(null);
        searchComment.or(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("commentText"), String.format("%%%s%%", searchWord))));
        return commentRepository.findAll(searchComment);

    }
}
