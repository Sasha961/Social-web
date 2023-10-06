package com.example.web.service;

import com.example.web.dto.PostStatisticRequestDto;
import org.springframework.http.ResponseEntity;

public interface StatisticService {

    ResponseEntity<Void> postStatistic (PostStatisticRequestDto postStatisticRequest);

    ResponseEntity<Void> likeStatistic (PostStatisticRequestDto postStatisticRequest);

    ResponseEntity<Void> commentStatistic (PostStatisticRequestDto postStatisticRequest);
}
