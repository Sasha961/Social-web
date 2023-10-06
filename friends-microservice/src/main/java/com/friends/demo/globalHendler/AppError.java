package com.friends.demo.globalHendler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppError {

    private String message;
    private int statusCode;
}
