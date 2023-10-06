package com.example.demo.service.Impl;

import com.example.demo.controller.feignClient.ControllerFromUsers;
import com.example.demo.dto.account.AccountCountPerAgeDto;
import com.example.demo.dto.account.AccountStatisticRequestDto;
import com.example.demo.dto.account.AccountStatisticResponseDto;
import com.example.demo.dto.statistic.AllStatisticDto;
import com.example.demo.dto.statistic.PostStatisticRequestDto;
import com.example.demo.dto.statistic.StatisticPerDateDto;
import com.example.demo.dto.statistic.StatisticResponseDto;
import com.example.demo.controller.feignClient.ControllerFromPosts;
import com.example.demo.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {


    private final ControllerFromUsers accountControllerFromUsers;

    private final ControllerFromPosts controllerFromPosts;


    @Override
    public AllStatisticDto getAllStatistic() {
        AllStatisticDto allStatisticDto = new AllStatisticDto();
//        allStatisticDto.setUsers(accountControllerFromUsers.getAllUsersCount());
//        allStatisticDto.setComments(controllerFromPosts.allCountComment());
        allStatisticDto.setLikes(500L);
//        allStatisticDto.setPublications(controllerFromPosts.allCountPosts());

        return allStatisticDto;
    }

    @Override
    public StatisticResponseDto postStatistic(@RequestBody(required = false) PostStatisticRequestDto statisticRequest) {
//        controllerFromPosts.postStatistic(statisticRequest);
        return getStatistic();
    }

    @Override
    public StatisticResponseDto likeStatistic(@RequestBody(required = false) PostStatisticRequestDto postStatisticRequest) {
//        controllerFromPosts.likeStatistic(postStatisticRequest);
        return getStatistic();
    }

    @Override
    public StatisticResponseDto commentStatistic(@RequestBody(required = false) PostStatisticRequestDto postStatisticRequest) {
//        controllerFromPosts.commentStatistic(postStatisticRequest);
        return getStatistic();
    }

    @Override
    public AccountStatisticResponseDto accountStatistic(@RequestBody(required = false) AccountStatisticRequestDto accountStatisticRequest) {
//        controllerFromUsers.getListAllAccounts();
        return  getAccountStatistic();
    }

    private AccountStatisticResponseDto getAccountStatistic(){
        StatisticPerDateDto statisticPerDate = new StatisticPerDateDto();
        statisticPerDate.setDate(new Date());
        statisticPerDate.setCount(0);

        AccountCountPerAgeDto accountCountPerAge = new AccountCountPerAgeDto();
        accountCountPerAge.setAge(0);
        accountCountPerAge.setCount(0);
        List<AccountCountPerAgeDto> countPerAges = new ArrayList<>();
        List<StatisticPerDateDto> listPerMouth = new ArrayList<>();

        countPerAges.add(accountCountPerAge);
        listPerMouth.add(statisticPerDate);

        AccountStatisticResponseDto accountStatisticResponse = new AccountStatisticResponseDto();
        accountStatisticResponse.setDate(new Date());
        accountStatisticResponse.setCount(0);
        accountStatisticResponse.setCountPerAge(countPerAges);
        accountStatisticResponse.setCountPerMonth(listPerMouth);

        return accountStatisticResponse;
    }

//    потом будет лист из БД
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
}
