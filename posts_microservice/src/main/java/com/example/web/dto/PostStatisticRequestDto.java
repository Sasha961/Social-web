package com.example.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

//DTO для запроса статистики

@Data
public class PostStatisticRequestDto {


    LocalDateTime date;

    LocalDateTime firstMonth;

    LocalDateTime lastMonth;
    }
