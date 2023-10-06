package com.example.web.controllers;

import com.example.web.dto.PostStatisticRequestDto;
import com.example.web.service.StatisticServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor

public class StatisticController {

    private final StatisticServiceImpl statisticService;

    //Получение статистики постов
    @GetMapping(value = "/api/v1/post/statistic/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postStatistic(PostStatisticRequestDto postStatisticRequest) {
        return statisticService.postStatistic(postStatisticRequest);
    }

    //Получение статистики лайков
    @GetMapping(value = "/api/v1/post/statistic/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> likeStatistic(PostStatisticRequestDto postStatisticRequest) {
        return statisticService.likeStatistic(postStatisticRequest);
    }

    //Получение статистики комментариев
    @GetMapping(value = "/api/v1/post/statistic/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> commentStatistic(PostStatisticRequestDto postStatisticRequest) {
        return statisticService.commentStatistic(postStatisticRequest);
    }
}
