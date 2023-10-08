package com.example.dto;

import lombok.Builder;
import lombok.Data;

//Список типов реакций
@Data
@Builder
public class ReactionDto {

    String reactionType;
    Long count;
}
