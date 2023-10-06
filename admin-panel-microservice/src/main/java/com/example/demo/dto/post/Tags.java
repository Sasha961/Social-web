package com.example.demo.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//Теги поста

@Data
@Schema
public class Tags {

    String tags;
}
