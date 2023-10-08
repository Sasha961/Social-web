package com.example.service;

import com.example.model.Comment;
import com.example.model.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostsSpecification {

    public static Specification<Comment> findByComment(String searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("commentText")), String.format("%%%s%%", searchWord).toLowerCase());
    }

    public static Specification<Post> findByPostTitle(String searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), String.format("%%%s%%", searchWord).toLowerCase());
    }

    public static Specification<Post> findByPostText(String searchWord) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("postText")), String.format("%%%s%%", searchWord).toLowerCase());
    }
}