package com.example.web.dto;

import lombok.Data;

//DTO тега

@Data
public class TagDto {

    int id;

    boolean isDeleted;

    //Имя тега
    String name;
}
