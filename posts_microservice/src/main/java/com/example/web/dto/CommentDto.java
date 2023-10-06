package com.example.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

//DTO коммента

@Data
public class CommentDto {

    String id;

    boolean  isDeleted;

    //    Тип комментария: POST - комментарий к посту, COMMENT - комментарий к
    //    комментарию, субкомментарий
    CommentTypeEnum commentType;

    // Время создания комментария
    LocalDateTime time;

    // Время изменения комментария
    LocalDateTime timeChanged;

    // ID автора комментария
    String authorId;

    // ID родителя, к которому был оставлен комментарий
    String parentId;

    //Текст комментария
    String commentText;

    //ID поста, к которому относится комментарий
    String postId;

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
