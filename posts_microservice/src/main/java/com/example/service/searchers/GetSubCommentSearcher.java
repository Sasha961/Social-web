package com.example.service.searchers;

import com.example.dto.CommentSearchDto;
import com.example.dto.CommentTypeEnum;
import com.example.model.Comment;
import com.example.repository.CommentRepository;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;

public class GetSubCommentSearcher {

    private CommentRepository commentRepository;
    public Specification<Comment> subCommentSearch(UUID postID, UUID commentId, UUID id, Boolean isDeleted,
                                                   CommentTypeEnum commentType,
                                                   Long authorId, UUID parentId) {

        //ID поста приходит и в CommentSearchDto и отдельно

        Specification<Comment> specification = Specification.where(null);

//        if (commentSearch.getId() != null) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"), String.format("%%%s%%", commentSearch.getId().toString()))));
//        }
//
//        if (commentSearch.isDeleted()) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("is_deleted"))));
//        }
//
//        //'Тип комментария: POST, COMMENT'
//        if (commentSearch.getCommentType() != null) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("comment_type"), String.format("%%%s%%", commentSearch.getCommentType().toString()))));
//        }
//
//        //ID автора комментария
//        if (commentSearch.getAuthorId() != null) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("author_id"), String.format("%%%s%%", commentSearch.getAuthorId().toString()))));
//        }
//
        specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("authorId"))));

//        //ID родителя комментария
//        if (commentSearch.getParentId() != null) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("parent_id"), String.format("%%%s%%", commentSearch.getParentId().toString()))));
//        }
//
//        //ID поста, к которому относится комментарий
//        if (commentSearch.getPostId() != null) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("post_id"), String.format("%%%s%%", commentSearch.getPostId().toString()))));
//        }

        return specification;
    }
}
