package com.example.demo.controller;


import com.example.demo.controller.feignClient.UsersControllerFeign;
import com.example.demo.dto.account.AccountDto;
import com.example.demo.dto.account.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersControllerFeign controllerFromUsers;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void  getAllAdminsTest() throws Exception {
        AccountDto adminDto = new AccountDto();
        adminDto.setEmail("admin@admin.admin");
        adminDto.setPassword("1234567");
        adminDto.setAbout("I'm admin");

        List<AccountDto> adminDtoList = new ArrayList<>();
        adminDtoList.add(adminDto);

        given(controllerFromUsers.getAllAdmins()).willReturn(ResponseEntity.ok(adminDtoList));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/moderators/all-admins"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void editAdminTest() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail("aaaa");
        accountDto.setPassword("1234567");

        given(controllerFromUsers.editAccountIfLogin()).willReturn(ResponseEntity.ok(accountDto));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/admin/moderators/edit-admin").param("email","admin@admin.admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteAdminTest() throws Exception {

        given(controllerFromUsers.deleteAccountById()).willReturn(ResponseEntity.ok("why?"));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/admin/moderators/delete-admin").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void addAdminTest() throws Exception {

        User user = new User();
        user.setFirstName("admin");
        user.setLastName("admin");

        AccountDto accountDto = new AccountDto();
        accountDto.setEmail("11111");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String request = objectWriter.writeValueAsString(user);

        given(controllerFromUsers.createAccount()).willReturn(ResponseEntity.ok(accountDto));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/moderators/add-admin").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
