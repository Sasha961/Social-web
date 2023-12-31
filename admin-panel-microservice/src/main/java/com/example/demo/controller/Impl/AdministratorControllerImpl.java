package com.example.demo.controller.Impl;

import com.example.demo.controller.AdministratorController;
import com.example.demo.controller.feignClient.UsersControllerFeign;
import com.example.demo.dto.account.AccountDto;
import com.example.demo.dto.account.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/moderators")
@SecurityRequirement(name = "JWT")
@Tag(name = "Admin service", description = "Работа с администраторами")
public class AdministratorControllerImpl implements AdministratorController {

    private final UsersControllerFeign usersControllerFeign;

    @Override
    public List<AccountDto> getAllAdmins() {
        return usersControllerFeign.getAllAdmins().getBody();
    }

    @Override
    public AccountDto editAdmin(String email) {
        return usersControllerFeign.editAccountIfLogin().getBody();
    }

    @Override
    public void deleteAdmin(Integer id) {
        usersControllerFeign.deleteAccountById();
    }

    @Override
    public void addAdmin(User user) {
        usersControllerFeign.createAccount(user);
    }
}
