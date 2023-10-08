package com.example.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

//DTO лайка
@Data
public class LikeDto {

    UUID id;
    Boolean isDeleted;
    Long authorId;              //ID автора комментария
    LocalDateTime time;         //Дата создания лайка
    UUID itemId;                //ID поста или комментария, к которому принадлежит лайк
    CommentTypeEnum type;       //'Тип лайка: POST - лайк на пост, COMMENT- лайк на комментарий'
    String reactionType;        //Тип реакции лайка
}
