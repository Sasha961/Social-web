package com.example.demo.controller;

import com.example.demo.dto.StorageDto;
import com.example.demo.service.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class StorageControllerTest {

    @MockBean
    private StorageService storageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
    public void storageTest() throws Exception {

        MultipartFile multipartFile = new MockMultipartFile("image", "image.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "TEST".getBytes());
        StorageDto storageDto = new StorageDto();
        storageDto.setFileName("/eureka_server/file/example.png");

        given(storageService.downloadFile(multipartFile)).willReturn(storageDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin-console/storage").contentType(MediaType.MULTIPART_FORM_DATA_VALUE).content(multipartFile.getBytes()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
