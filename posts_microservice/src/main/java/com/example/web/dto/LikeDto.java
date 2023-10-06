package com.example.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

//DTO лайка

@Data
public class LikeDto {

    String id;

    boolean isDeleted;

    //ID автора комментария
    String authorId;

    //Дата создания лайка
    LocalDateTime time;

    //ID поста или комментария, к которому принадлежит лайк
    String itemId;

    //'Тип лайка: POST - лайк на пост, COMMENT- лайк на комментарий'
    CommentTypeEnum type;

    //Тип реакции лайка
    String reactionType;
}
