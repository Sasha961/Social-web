package com.example.demo;

import com.example.demo.controller.feignClient.UsersControllerFeign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersControllerFeign controllerFromUsers;

    @Test
    public void securityAccessAllowedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui/index.html#/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void securityAccessDeniedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/moderators/all-admins"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void securityCheckTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/admin/moderators/all-admins"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
}
