package com.example.dto;

import lombok.Data;
import java.util.UUID;

//DTO тега для поиска

@Data
public class TagSearchDto {

    UUID id;
    Boolean isDeleted;
    String name;        //Имя тега для поиска
}
