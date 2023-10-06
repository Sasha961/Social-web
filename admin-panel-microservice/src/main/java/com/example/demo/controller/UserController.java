package com.example.demo.controller;

import com.example.demo.dto.account.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserController {

    @GetMapping(value = "/search-user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Поиск людей")
    List<User> searchUser(@PathVariable(value = "username", required = false) String username);

    @PutMapping(value = "/block-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Блокировка пользователя")
    void blockUser(@RequestParam(value = "id") Long id);

    @PutMapping(value = "/unblock-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Разблокировка пользователя")
    void unblockUser(@RequestParam(value = "id") Long id);

}
