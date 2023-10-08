package com.example.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//DTO для поиска постов
@Data
@Builder
public class PostSearchDto {

    UUID id;
    Boolean isDeleted;
    List<UUID> ids;         //ID постов
    List<Long> accountIds;  //ID аккаунтов авторов постов
    List<UUID> blockedIds;  //ID заблокированных аккаунтов авторов постов
    String author;          //Автор
    Boolean withFriends;    //С друзьями?
    List<String> tags;      //Теги поста
    LocalDateTime dateFrom; //Дата от
    LocalDateTime dateTo;   //Дата до
}
