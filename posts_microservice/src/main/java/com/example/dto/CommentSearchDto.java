package com.example.dto;

import lombok.Data;
import java.util.UUID;

//DTO для поиска комментов
@Data
public class CommentSearchDto {

    UUID id;
    Boolean isDeleted;
    CommentTypeEnum commentType;    //'Тип комментария: POST, COMMENT'
    Long authorId;                  //ID автора комментария
    UUID parentId;                  //ID родителя комментария
    UUID postId;                    //ID поста, к которому относится комментарий
}
