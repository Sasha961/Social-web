package com.example.demo.controller;

import com.example.demo.dto.error.BadRequestException;
import com.example.demo.dto.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler
    public ResponseEntity<ErrorDto> errorResponse(BadRequestException ex){
        return new ResponseEntity<>(new ErrorDto("нет данных"), HttpStatus.BAD_REQUEST);

    }
}
