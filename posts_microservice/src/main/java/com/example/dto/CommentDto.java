package com.example.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

//DTO коммента
@Data
public class CommentDto {

    UUID id;
    Boolean  isDeleted;
    CommentTypeEnum commentType;    //    Тип комментария: POST - к посту, COMMENT - к комментарию, субкомментарий
    LocalDateTime time;             // Время создания комментария
    LocalDateTime timeChanged;      // Время изменения комментария
    Long authorId;                  // ID автора комментария
    UUID parentId;                  // ID родителя, к которому был оставлен комментарий
    String commentText;             //Текст комментария
    UUID postId;                    //ID поста, к которому относится комментарий
    Boolean isBlocked;              //Заблокирован ли?
    Long likeAmount;                //Количество лайков комментария
    Boolean myLike;                 // Это твой лайк?
    Long commentsCount;             //Количество комментариев
    String imagePath;               //Путь к изображению
}
