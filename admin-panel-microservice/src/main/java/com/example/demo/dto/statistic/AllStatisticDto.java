package com.example.demo.dto.statistic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class AllStatisticDto {


    Long users;

    Long publications;

    Long comments;

    Long likes;
}
