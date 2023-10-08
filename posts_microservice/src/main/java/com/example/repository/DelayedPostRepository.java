package com.example.repository;

import com.example.model.DelayedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DelayedPostRepository extends JpaRepository<DelayedPost, Long>, JpaSpecificationExecutor<DelayedPost> {
    void deleteById(UUID id);
}
