package com.example.demo.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class AccountCountPerAgeDto {

    public Integer age;

    public Integer count;
}
