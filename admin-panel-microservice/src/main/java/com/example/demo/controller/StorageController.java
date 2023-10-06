package com.example.demo.controller;

import com.example.demo.dto.StorageDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface StorageController {

    @PostMapping(value = "/storage",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Получение ссылки на загруженный файл")
    StorageDto storage(@RequestParam MultipartFile file) throws Exception;
}
