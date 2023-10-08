package com.example.demo.service;

import com.example.demo.dto.statistic.AllStatisticDto;
import com.example.demo.dto.statistic.StatisticPerDateDto;
import com.example.demo.dto.statistic.StatisticResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class StatisticServiceTest {

    @Autowired
    private StatisticService statisticService;

//    @Autowired
//    private ControllerFromPosts controllerFromPosts;
//
//    @Autowired
//    private ControllerFromUsers controllerFromUsers;

    public StatisticResponseDto getStatistic(){

        StatisticPerDateDto statisticPerDate = new StatisticPerDateDto();
        statisticPerDate.setDate(new Date());
        statisticPerDate.setCount(0);

        List<StatisticPerDateDto> listPerHours = new ArrayList<>();
        List<StatisticPerDateDto> listPerMouth = new ArrayList<>();

        listPerHours.add(statisticPerDate);
        listPerMouth.add(statisticPerDate);

        StatisticResponseDto statisticResponse = new StatisticResponseDto();
        statisticResponse.setDate(new Date());
        statisticResponse.setCount(0);
        statisticResponse.setCountPerHours(listPerHours);
        statisticResponse.setCountPerMonth(listPerMouth);

        return statisticResponse;
    }



    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void getAllStatisticTest() {

        AllStatisticDto allStatisticDto = new AllStatisticDto();

        Assertions.assertEquals(statisticService.getAllStatistic(), allStatisticDto);
    }

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void postStatisticTest() {
//
//        PostStatisticRequestDto postStatisticRequestDto = new PostStatisticRequestDto();
//        Assertions.assertEquals(statisticService.postStatistic(postStatisticRequestDto), getStatistic());
//    }


}
