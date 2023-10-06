package com.example.demo.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema
@AllArgsConstructor
public class ErrorDto {

    private String message;


}
