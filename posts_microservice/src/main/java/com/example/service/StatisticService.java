package com.example.service;

import com.example.dto.PostStatisticRequestDto;

public interface StatisticService {

    long postStatistic (PostStatisticRequestDto postStatisticRequest);

    long likeStatistic (PostStatisticRequestDto postStatisticRequest);

    long commentStatistic (PostStatisticRequestDto postStatisticRequest);
}
