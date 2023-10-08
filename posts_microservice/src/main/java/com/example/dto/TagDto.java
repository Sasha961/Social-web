package com.example.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

//DTO тега
@Data
@Builder
public class TagDto {

    UUID id;
    boolean isDeleted;
    String name;        //Имя тега
}
