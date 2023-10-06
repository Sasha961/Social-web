package com.example.demo.controller;

import com.example.demo.dto.account.AccountCountPerAgeDto;
import com.example.demo.dto.account.AccountStatisticRequestDto;
import com.example.demo.dto.account.AccountStatisticResponseDto;
import com.example.demo.dto.statistic.AllStatisticDto;
import com.example.demo.dto.statistic.PostStatisticRequestDto;
import com.example.demo.dto.statistic.StatisticPerDateDto;
import com.example.demo.dto.statistic.StatisticResponseDto;
import com.example.demo.service.StatisticService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticService statisticService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void  getAllAdminsTest() throws Exception {

        AllStatisticDto allStatisticDto = new AllStatisticDto();
        allStatisticDto.setLikes(100L);
        allStatisticDto.setPublications(200L);
        allStatisticDto.setComments(123L);
        allStatisticDto.setUsers(500_000L);

        given(statisticService.getAllStatistic()).willReturn(allStatisticDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/statistics/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());
    }

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void postStatisticTest() throws Exception {
//
//        StatisticPerDateDto statisticPerDate = new StatisticPerDateDto();
//        statisticPerDate.setDate(new Date());
//        statisticPerDate.setCount(0);
//
//        List<StatisticPerDateDto> listPerHours = new ArrayList<>();
//        List<StatisticPerDateDto> listPerMouth = new ArrayList<>();
//
//        listPerHours.add(statisticPerDate);
//        listPerMouth.add(statisticPerDate);
//
//        StatisticResponseDto statisticResponse = new StatisticResponseDto();
//        statisticResponse.setDate(new Date());
//        statisticResponse.setCount(0);
//        statisticResponse.setCountPerHours(listPerHours);
//        statisticResponse.setCountPerMonth(listPerMouth);
//
//        PostStatisticRequestDto postStatisticRequestDto = new PostStatisticRequestDto();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
//        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
//        String request = objectWriter.writeValueAsString(postStatisticRequestDto);
//
//        given(statisticService.postStatistic(postStatisticRequestDto)).willReturn(statisticResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/admin/statistics/post").contentType(MediaType.APPLICATION_JSON).content(request))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void likeStatisticTest() throws Exception {

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

        PostStatisticRequestDto postStatisticRequestDto = new PostStatisticRequestDto();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String request = objectWriter.writeValueAsString(postStatisticRequestDto);

        given(statisticService.likeStatistic(postStatisticRequestDto)).willReturn(statisticResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/statistics/like").contentType(MediaType.APPLICATION_JSON).content(request))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void commentStatisticTest() throws Exception {

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

        PostStatisticRequestDto postStatisticRequestDto = new PostStatisticRequestDto();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String request = objectWriter.writeValueAsString(postStatisticRequestDto);

        given(statisticService.commentStatistic(postStatisticRequestDto)).willReturn(statisticResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/statistics/comment")
                        .contentType(MediaType.APPLICATION_JSON).content(request))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void AccountStatisticTest() throws Exception {

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

        AccountStatisticRequestDto accountStatisticRequestDto = new AccountStatisticRequestDto();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String request = objectWriter.writeValueAsString(accountStatisticRequestDto);

        given(statisticService.accountStatistic(accountStatisticRequestDto)).willReturn(accountStatisticResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/statistics/account").contentType(MediaType.APPLICATION_JSON).content(request))
                .andDo(print())
                .andExpect(status().isOk());

    }


}
