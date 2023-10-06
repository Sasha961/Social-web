package com.example.demo.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@Schema
public class BadRequestException extends HttpClientErrorException {
    public BadRequestException(HttpStatus statusCode) {
        super(statusCode);
    }
}
