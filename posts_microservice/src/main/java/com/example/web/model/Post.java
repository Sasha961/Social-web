package com.example.web.model;

import com.example.web.dto.ReactionDto;
import com.example.web.dto.TagDto;
import com.example.web.dto.TypePostEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "post")
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
