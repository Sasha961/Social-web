package com.example.demo.service.Impl;

import com.demo.storage.Storage;
import com.example.demo.dto.StorageDto;
import com.example.demo.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySource("classpath:/yandexCloud.properties")
public class StorageServiceImpl implements StorageService {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${access.key}")
    private String accessKey;

    @Value("${secret.key}")
    private String secretKey;

    @Value("${service.endpoint}")
    private String serviceEndpoint;

    @Value("${signing.region}")
    private String signingRegion;

    @Override
    public StorageDto downloadFile(MultipartFile file) {
        Storage storage = new Storage(uploadPath, accessKey, secretKey, serviceEndpoint, signingRegion);
        try {
            storage.addFile("social-web-bucket2", file);
        } catch (Exception e) {
            throw new RuntimeException("Превышен Лимит в 10 мб");
        }
        String url = storage.getUrl(file.getOriginalFilename(), "social-web-bucket2");
        StorageDto storageDto = new StorageDto();
        storageDto.setFileName(url);
        return storageDto;
    }
}
