package com.example.demo.dto.security;

import lombok.Data;

import java.util.Date;

@Data
public class AuthErrorDto {

    private Integer status;

    private String message;

    private Date timestamp;

    public AuthErrorDto(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
