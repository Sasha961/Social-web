package com.example.demo.dto.security;

import lombok.Data;

@Data
public class JWTRequestDto {

    private String username;

    private String password;
}
