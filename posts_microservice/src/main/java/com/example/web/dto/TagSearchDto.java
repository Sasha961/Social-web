package com.example.web.dto;

import lombok.Data;

//DTO тега для поиска

@Data
public class TagSearchDto {

    String id;

    boolean isDeleted;

    //Имя тега для поиска
    String name;
}
