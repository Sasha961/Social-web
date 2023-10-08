package com.example.demo.service;

import com.example.demo.dto.StorageDto;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    StorageDto downloadFile(MultipartFile file) throws Exception;
}
