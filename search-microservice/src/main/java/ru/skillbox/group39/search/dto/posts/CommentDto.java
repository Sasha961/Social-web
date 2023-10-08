package ru.skillbox.group39.search.dto.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

//DTO коммента

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema
public class CommentDto {

    UUID id;

    boolean  isDeleted;

    //    Тип комментария: POST - комментарий к посту, COMMENT - комментарий к
    //    комментарию, субкомментарий
    CommentTypeEnum commentType;

    // Время создания комментария
    LocalDateTime time;

    // Время изменения комментария
    LocalDateTime timeChanged;

    // ID автора комментария
    UUID authorId;

    // ID родителя, к которому был оставлен комментарий
    UUID parentId;

    //Текст комментария
    String commentText;

    //ID поста, к которому относится комментарий
    UUID postId;

    //Заблокирован ли?
    boolean isBlocked;

    //Количество лайков комментария
    long likeAmount;

    // Это твой лайк?
    boolean myLike;

    //Количество комментариев
    long commentsCount;

    //Путь к изображению
    String imagePath;
}
