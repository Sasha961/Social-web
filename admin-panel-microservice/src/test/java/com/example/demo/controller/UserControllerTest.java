package com.example.demo.controller;

import com.example.demo.controller.feignClient.ControllerFromUsers;
import com.example.demo.dto.account.User;
import com.example.demo.service.security.JWT.JwtFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControllerFromUsers controllerFromUsers;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void searchUserTest() throws Exception {

        String word = "search";

        User user = new User();
        user.setLastName("testUser");
        user.setFirstName("testUser");
        user.setRoles("ROLE_ADMIN");


        List<User> users = new ArrayList<>();
        users.add(user);


        given(controllerFromUsers.getUserByName(word)).willReturn(ResponseEntity.ok(users));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/users/search-user/{username}", "search"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void blockUserTest() throws Exception {

        given(controllerFromUsers.blockUser(1L)).willReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/admin/users/block-user").param("id","1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void unblockUserTest() throws Exception {

        given(controllerFromUsers.blockUser(1L)).willReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/admin/users/unblock-user").param("id","1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}