package com.example.demo.service.Impl;

import com.example.demo.controller.feignClient.UsersControllerFeign;
import com.example.demo.dto.account.AccountCountPerAgeDto;
import com.example.demo.dto.account.AccountStatisticRequestDto;
import com.example.demo.dto.account.AccountStatisticResponseDto;
import com.example.demo.dto.statistic.AllStatisticDto;
import com.example.demo.dto.statistic.PostStatisticRequestDto;
import com.example.demo.dto.statistic.StatisticPerDateDto;
import com.example.demo.dto.statistic.StatisticResponseDto;
import com.example.demo.controller.feignClient.PostsControllerFeign;
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


    private final UsersControllerFeign usersControllerFeign;

    private final PostsControllerFeign postsControllerFeign;


    @Override
    public AllStatisticDto getAllStatistic() {
        AllStatisticDto allStatisticDto = new AllStatisticDto();
//        allStatisticDto.setUsers(usersControllerFeign.getAllUsersCount());
//        allStatisticDto.setComments(postsControllerFeign.allCountComment());
//        allStatisticDto.setLikes(postsControllerFeign.allCountLikes());
//        allStatisticDto.setPublications(postsControllerFeign.allCountPosts());

        return allStatisticDto;
    }

    @Override
    public StatisticResponseDto postStatistic(@RequestBody(required = false) PostStatisticRequestDto statisticRequest) {
//        postsControllerFeign.postStatistic(statisticRequest);
        return getStatistPlug();
    }

    @Override
    public StatisticResponseDto likeStatistic(@RequestBody(required = false) PostStatisticRequestDto postStatisticRequest) {
//        postsControllerFeign.likeStatistic(postStatisticRequest);
        return getStatistPlug();
    }

    @Override
    public StatisticResponseDto commentStatistic(@RequestBody(required = false) PostStatisticRequestDto postStatisticRequest) {
//        postsControllerFeign.commentStatistic(postStatisticRequest);
        return getStatistPlug();
    }

    @Override
    public AccountStatisticResponseDto accountStatistic(@RequestBody(required = false) AccountStatisticRequestDto accountStatisticRequest) {
//        controllerFromUsers.getListAllAccounts();
        return getAccountStatisticPlug();
    }

    private AccountStatisticResponseDto getAccountStatisticPlug() {
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

    public StatisticResponseDto getStatistPlug() {

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
