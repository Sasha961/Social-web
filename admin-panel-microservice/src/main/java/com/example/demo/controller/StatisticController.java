package com.example.demo.controller;

import com.example.demo.dto.account.AccountStatisticRequestDto;
import com.example.demo.dto.account.AccountStatisticResponseDto;
import com.example.demo.dto.statistic.AllStatisticDto;
import com.example.demo.dto.statistic.PostStatisticRequestDto;
import com.example.demo.dto.statistic.StatisticResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public interface StatisticController {

    @GetMapping(value = "/all")
    @Operation(description = "Общая статистика")
    AllStatisticDto getAllStatistic();

    @GetMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Получение статистики постов")
    StatisticResponseDto postStatistic(@RequestBody(required = false) PostStatisticRequestDto postStatisticRequest);

    @GetMapping(value = "/like", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Получение статистики лайков")
    StatisticResponseDto likeStatistic(@RequestBody(required = false) PostStatisticRequestDto postStatisticRequest);

    @GetMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Получение статистики комментариев")
    StatisticResponseDto commentStatistic(@RequestBody(required = false) PostStatisticRequestDto postStatisticRequest);

    @GetMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Получение статистики аккаунтов")
    AccountStatisticResponseDto accountStatistic(@RequestBody(required = false) AccountStatisticRequestDto accountStatisticRequest);
}
