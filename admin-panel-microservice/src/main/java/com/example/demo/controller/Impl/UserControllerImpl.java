package com.example.demo.controller.Impl;


import com.example.demo.controller.UserController;
import com.example.demo.controller.feignClient.UsersControllerFeign;
import com.example.demo.dto.account.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@SecurityRequirement(name = "JWT")
@Tag(name = "UserService", description = "Работа с пользователями")
public class UserControllerImpl implements UserController {

    private final UsersControllerFeign usersControllerFeign;

    @Override
    public List<User> searchUser(String username) {
        return usersControllerFeign.getUserByName(username).getBody();
    }

    @Override
    public void blockUser(Long id) {
        usersControllerFeign.blockUser(id);
    }

    @Override
    public void unblockUser(Long id) {
        usersControllerFeign.blockUser(id);
    }
}
