package com.example.demo.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

//DTO поста

@Data
@Schema
public class PostDto {

    String id;

    boolean isDeleted;

    //Время создания поста
    LocalDateTime time;

    //Время изменения поста
    LocalDateTime timeChanged;

    //ID автора поста
    String authorId;

    //Заголовок поста
    String title;

    //Тип поста
    TypePostEnum type;

    //'Текст поста: POSTED - опубликован, QUEUED - отложен)'
    String postText;

    //Заблокирован ли пост?
    boolean isBlocked;

    //Количество комментариев к посту
    int commentsCount;

    //Теги поста
    TagDto tags;

    //Список типов реакций
    ReactionDto reactions;

    //Тип реакции пользователя
    String myReaction;

    //Количество лайков
    int likeAmount;

    //Есть мой лайк?
    boolean myLike;

    //Путь к изображению
    String imagePath;

    //Дата и время публикации поста
    LocalDateTime publishDate;
}
