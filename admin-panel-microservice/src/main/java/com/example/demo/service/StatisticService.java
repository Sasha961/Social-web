package com.example.demo.service;

import com.example.demo.dto.account.AccountStatisticRequestDto;
import com.example.demo.dto.account.AccountStatisticResponseDto;
import com.example.demo.dto.statistic.AllStatisticDto;
import com.example.demo.dto.statistic.PostStatisticRequestDto;
import com.example.demo.dto.statistic.StatisticResponseDto;

public interface StatisticService {

    AllStatisticDto getAllStatistic();

    StatisticResponseDto postStatistic(PostStatisticRequestDto statisticRequest);

    StatisticResponseDto likeStatistic(PostStatisticRequestDto postStatisticRequest);

    StatisticResponseDto commentStatistic(PostStatisticRequestDto postStatisticRequest);

    AccountStatisticResponseDto accountStatistic(AccountStatisticRequestDto accountStatisticRequest);
}
