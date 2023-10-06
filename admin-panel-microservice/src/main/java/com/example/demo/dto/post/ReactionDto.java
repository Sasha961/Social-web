package com.example.demo.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//Список типов реакций

@Data
@Schema
public class ReactionDto {

    String reactionType;

    long count;
}
