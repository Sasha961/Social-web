package com.example.service.searchers;

import com.example.model.Post;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GetPostsSearcher {

        public Specification<Post> postsSearch(UUID id, Boolean isDeleted, List<UUID> ids, List<Long> accountIds, List<UUID> blockedIds,
                                               String author, Boolean withFriends, List<String> tags, LocalDateTime dateFrom,
                                               LocalDateTime dateTo) {

        Specification<Post> specification = Specification.where(null);

        if (id != null) {
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id)));
        }
//        if (!isDeleted) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("is_deleted"))));
//        }


//        //ID постов ????????
//        //Ids ids;
//        //if (postSearch.getIds() != null) {
//            //    for (idPost : postSearch.getIds()) {
//            //        specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"), String.format("%%%s%%", idPost))));
//            //    }
//        //}
//
        //ID аккаунтов авторов постов   ??????
        //AccountIds accountIds;
        if (accountIds != null && !accountIds.contains(102L)) {      // тут стоит временный костыль, чтобы по моему айдищнику мне все посты были видны
                for (Long accountId : accountIds) {
                    if (accountId != null) {
                        specification = specification.or(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("authorId"), accountId)));
                    }
                }
        }
        specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("authorId"))));

//       // ID заблокированных аккаунтов авторов постов   ??? разобраться с типом
//        //BlockedIds blockedIds;
//        if (postSearch.getBlockedIds() != null) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("is_blocked"), String.format("%%%s%%", postSearch.getId().toString()))));
//        }

        //Автор   ??? приходит в String хранится в UUID  может это имя?
        //String author;
//        if (author != null) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("author_id"), String.format("%%%s%%", author))));
//        }
//
//        //С друзьями ??? по каким полям, кого искать
//        //boolean withFriends;
//        if (!withFriends) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("withFriends"))));
//        }
//
//        //Теги поста
//        if (tags != null) {
//            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("tags"), String.format("%%%s%%", tags))));
//        }

        //Дата от
        if (dateFrom != null) {
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("publishDate"), dateFrom)));
        }

        //Дата до
        if (dateTo != null) {
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("publishDate"), dateTo)));
        }

        return specification;
    }
}
