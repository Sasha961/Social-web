package ru.skillbox.group39.search.dto.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

//DTO поста

//@Data
@Setter
@Getter
@Schema
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto {

    UUID id;

    boolean isDeleted;

    //Время создания поста
    LocalDateTime timeCreated;

    //Время изменения поста
    LocalDateTime timeChanged;

    //ID автора поста
    UUID authorId;

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
    String tags;

    //Список типов реакций
    String reactions;

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
