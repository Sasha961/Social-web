package com.example.demo.service;

import com.example.demo.dto.StorageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    StorageDto downloadFile(MultipartFile file) throws Exception;
}
