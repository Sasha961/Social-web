package com.example.demo.service;


import com.example.demo.dto.StorageDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class StorageServiceTest {

    @Autowired
    private StorageService storageService;

    @Test
    public void storageTest() throws Exception {

        MultipartFile multipartFile = new MockMultipartFile("image", "image.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "TEST".getBytes());
        storageService.downloadFile(multipartFile);

        Assertions.assertEquals(storageService.downloadFile(multipartFile).getFileName(), "https://social-web-bucket2.storage.yandexcloud.net/image.txt");

    }

}
