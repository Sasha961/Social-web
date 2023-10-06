package com.example.demo.controller;

import com.example.demo.dto.account.AccountDto;
import com.example.demo.dto.account.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AdministratorController {

    @GetMapping(value = "/all-admins", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Список Админов")
    List<AccountDto> getAllAdmins();

    @PutMapping(value = "/edit-admin")
    @Operation(description = "редактирование")
    AccountDto editAdmin(@RequestParam(name = "email") String email);


    @DeleteMapping(value = "/delete-admin")
    @Operation(description = "удаление адимнистратора/модератора")
    void deleteAdmin(@RequestParam(name = "id") Integer id);

    @PostMapping(value = "/add-admin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "добавить администратора")
    void addAdmin(@RequestBody User user);
}
