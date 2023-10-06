package com.example.demo.dto.account;

import com.example.demo.dto.statistic.StatisticPerDateDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
@Schema
public class AccountStatisticResponseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public Date date;

    public Integer count;

    public List<AccountCountPerAgeDto> countPerAge;

    public List<StatisticPerDateDto> countPerMonth;
}
