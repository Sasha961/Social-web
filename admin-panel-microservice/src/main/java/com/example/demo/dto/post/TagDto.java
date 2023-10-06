package com.example.demo.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//DTO тега

@Data
@Schema
public class TagDto {

    int id;

    boolean isDeleted;

    //Имя тега
    String name;
}
