package com.example.demo.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//DTO для поиска комментов

@Data
@Schema
public class CommentSearchDto {

    String id;

    boolean isDeleted;

    //'Тип комментария: POST, COMMENT'
    CommentTypeEnum commentType;

    //ID автора комментария
    String authorId;

    //ID родителя комментария
    String parentId;

    //ID поста, к которому относится комментарий
    String postId;

}
