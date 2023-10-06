package com.example.demo.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

//DTO для поиска постов

@Data
@Schema
public class PostSearchDto {

    String id;

    boolean isDeleted;

    //ID постов
    Ids ids;

    //ID аккаунтов авторов постов
    AccountIds accountIds;

    //ID заблокированных аккаунтов авторов постов
    BlockedIds blockedIds;

    //Автор
    String author;

    //С друзьями?
    boolean withFriends;

    //Теги поста
    Tags tags;

    //Дата от
    LocalDateTime dateFrom;

    //Дата до
    LocalDateTime dateTo;
}
