package com.example.model;

import com.example.dto.TypePostEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    //Время создания поста
    @Column(name = "time_created")
    private LocalDateTime timeCreated;

    //Время изменения поста
    @Column(name = "time_changed")
    private LocalDateTime timeChanged;

    //ID автора поста
    @Column(name = "author_id")
    private Long authorId;

    //Заголовок поста
    @Column(name = "title")
    private String title;

    //Тип поста: POSTED - опубликован, QUEUED - отложен)
    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private TypePostEnum postType;

    //'Текст поста'
    @Column(name = "post_text")
    private String postText;

    //Заблокирован ли пост?
    @Column(name = "is_blocked")
    private Boolean isBlocked;

    //Количество комментариев к посту
    @Column(name = "comments_count")
    private Integer commentsCount;

    //Теги поста
    @Column(name = "tags")
    //private Tag tags;
    private String tags;

    //Список типов реакций
    @Column(name = "reactions")
    //private Reaction reactions;
    private String reactions;

    //Тип реакции пользователя
    @Column(name = "my_reaction")
    private String myReaction;

    //Количество лайков
    @Column(name = "like_amount")
    private Integer likeAmount;

    //Есть мой лайк?
    @Column(name = "my_like")
    private Boolean myLike;

    //Путь к изображению
    @Column(name = "image_path")
    private String imagePath;

    //Дата и время публикации поста
    @Column(name = "publish_date")
    private LocalDateTime publishDate;
}
