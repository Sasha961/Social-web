package com.example.web.service;

import com.example.web.dto.PostStatisticRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImpl implements StatisticService{

    @Override
    public ResponseEntity<Void> postStatistic(PostStatisticRequestDto postStatisticRequest) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> likeStatistic(PostStatisticRequestDto postStatisticRequest) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> commentStatistic(PostStatisticRequestDto postStatisticRequest) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
