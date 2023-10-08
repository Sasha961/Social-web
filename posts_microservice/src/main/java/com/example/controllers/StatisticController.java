package com.example.controllers;

import com.example.dto.PostStatisticRequestDto;
import com.example.service.StatisticServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticServiceImpl statisticService;

    @Operation(description = "Получение статистики постов", tags = {"Statistic service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping(value = "/api/v1/post/statistic/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public long postStatistic(PostStatisticRequestDto postStatisticRequest) {
        return statisticService.postStatistic(postStatisticRequest);
    }

    @Operation(description = "Получение статистики лайков", tags = {"Statistic service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping(value = "/api/v1/post/statistic/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public long likeStatistic(PostStatisticRequestDto postStatisticRequest) {
        return statisticService.likeStatistic(postStatisticRequest);
    }

    @Operation(description = "Получение статистики комментариев", tags = {"Statistic service"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping(value = "/api/v1/post/statistic/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public long commentStatistic(PostStatisticRequestDto postStatisticRequest) {
        return statisticService.commentStatistic(postStatisticRequest);
    }
}
