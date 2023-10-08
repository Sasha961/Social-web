package ru.skillbox.group39.friends.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = SecurityTest.class)
@ExtendWith(MockitoExtension.class)
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void securityAccessDeniedTest_returnAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/friends/count"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
