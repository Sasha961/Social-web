package com.example.repository;

import com.example.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    void deleteById(UUID id);

    Optional<Post> findById(UUID id);

    Post getReferenceById(UUID id);

    Post getById(UUID id);


}
