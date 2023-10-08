package com.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import reactor.util.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.UUID;

//DTO пост
@Data
//@Builder  // с билдером перестало работать получение поста, начало требовать конструктор без аргументов
        // с @NoArgsConstructor возникал конфликт
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto {

    @Nullable
    UUID id;
    @Nullable
    Boolean isDeleted;
    @Nullable
    LocalDateTime timeCreated;      //Время создания поста
    @Nullable
    LocalDateTime timeChanged;      //Время изменения поста
    @Nullable
    Long authorId;                  //ID автора поста
    @Nullable
    String title;                   //Заголовок поста
    @Nullable
    TypePostEnum type;              //Тип поста
    @Nullable
    String postText;                //'Текст поста: POSTED - опубликован, QUEUED - отложен)'
    @Nullable
    Boolean isBlocked;              //Заблокирован ли пост?
    @Nullable
    Integer commentsCount;          //Количество комментариев к посту
    @Nullable
    String tags;                    //Теги поста
    @Nullable
    String reactions;          //Список типов реакций
    @Nullable
    String myReaction;              //Тип реакции пользователя
    @Nullable
    Integer likeAmount;             //Количество лайков
    @Nullable
    Boolean myLike;                 //Есть мой лайк?
    @Nullable
    String imagePath;               //Путь к изображению
    @Nullable
    LocalDateTime publishDate;      //Дата и время публикации поста
}
