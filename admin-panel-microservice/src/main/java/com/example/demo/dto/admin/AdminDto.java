package com.example.demo.dto.admin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    private String name;

    private String email;

    private String password;

    private AdminType adminType;

}
