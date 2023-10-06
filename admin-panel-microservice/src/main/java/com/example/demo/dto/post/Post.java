package com.example.demo.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Schema
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    private boolean isDeleted;

    //Время создания поста
    private LocalDateTime time;

    //Время изменения поста
    private LocalDateTime timeChanged;

    //ID автора поста
    private long authorId;

    //Заголовок поста
    private String title;

    //Тип поста
    private TypePostEnum type;

    //'Текст поста: POSTED - опубликован, QUEUED - отложен)'
    private String postText;

    //Заблокирован ли пост?
    private boolean isBlocked;

    //Количество комментариев к посту
    private int commentsCount;

    //Теги поста
    private TagDto tags;

    //Список типов реакций
    private ReactionDto reactions;

    //Тип реакции пользователя
    private String myReaction;

    //Количество лайков
    private int likeAmount;

    //Есть мой лайк?
    private boolean myLike;

    //Путь к изображению
    private String imagePath;

    //Дата и время публикации поста
    private LocalDateTime publishDate;
}
