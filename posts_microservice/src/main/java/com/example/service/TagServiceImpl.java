package com.example.service;

import com.example.dto.TagDto;
import com.example.dto.TagSearchDto;
import com.example.model.Comment;
import com.example.repository.PostRepository;
import org.springframework.data.jpa.domain.Specification;

public class TagServiceImpl implements TagService{

    private PostRepository postRepository;
    @Override
    public TagDto tagSearch(TagSearchDto tagSearch) {

        Specification<Comment> specification = Specification.where(null);

        if (tagSearch.getId() != null) {
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"), String.format("%%%s%%", tagSearch.getId().toString()))));
        }

        if (tagSearch.getIsDeleted()) {
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("is_deleted"))));
        }

        if (tagSearch.getName() != null) {
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("comment_type"), String.format("%%%s%%", tagSearch.getName()))));
        }

        return null;
    }
}
