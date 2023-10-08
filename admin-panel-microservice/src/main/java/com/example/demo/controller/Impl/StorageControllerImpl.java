package com.example.demo.controller.Impl;

import com.example.demo.controller.StorageController;
import com.example.demo.dto.StorageDto;
import com.example.demo.service.StorageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin-console")
@SecurityRequirement(name = "JWT")
@Tag(name = "Storage service", description = "Работа с файлами")
public class StorageControllerImpl implements StorageController {

    private final StorageService storageService;

    @Override
    public StorageDto storage(@RequestPart(required = false) MultipartFile file) throws Exception {
        return storageService.downloadFile(file);
    }
}
