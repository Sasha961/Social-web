package com.example.demo.controller.Impl;

import com.example.demo.controller.StatisticController;
import com.example.demo.dto.account.AccountStatisticRequestDto;
import com.example.demo.dto.account.AccountStatisticResponseDto;
import com.example.demo.dto.statistic.AllStatisticDto;
import com.example.demo.dto.statistic.PostStatisticRequestDto;
import com.example.demo.dto.statistic.StatisticResponseDto;
import com.example.demo.service.StatisticService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/statistics")
@SecurityRequirement(name = "JWT")
@Tag(name = "StatisticService", description = "Работа со статистикой")
public class StatisticControllerImpl implements StatisticController {

    private final StatisticService statisticService;

    @Override
    public AllStatisticDto getAllStatistic() {
        return statisticService.getAllStatistic();
    }

    @Override
    public StatisticResponseDto postStatistic(@RequestParam(required = false) PostStatisticRequestDto postStatisticRequest) {
        return statisticService.postStatistic(postStatisticRequest);
    }

    @Override
    public StatisticResponseDto likeStatistic(@RequestParam(required = false) PostStatisticRequestDto postStatisticRequest) {
        return statisticService.likeStatistic(postStatisticRequest);
    }

    @Override
    public StatisticResponseDto commentStatistic(@RequestParam(required = false) PostStatisticRequestDto postStatisticRequest) {
        return statisticService.commentStatistic(postStatisticRequest);
    }

    @Override
    public AccountStatisticResponseDto accountStatistic(@RequestParam(required = false) AccountStatisticRequestDto accountStatisticRequest) {
        return statisticService.accountStatistic(accountStatisticRequest);
    }
}
