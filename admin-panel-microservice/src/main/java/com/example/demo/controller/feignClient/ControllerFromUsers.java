package com.example.demo.controller.feignClient;

import com.example.demo.config.FeignConfig;
import com.example.demo.dto.account.AccountDto;
import com.example.demo.dto.account.AccountStatisticRequestDto;
import com.example.demo.dto.post.PostSearchDto;
import com.example.demo.dto.account.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "AccountController", url = "http://localhost:8085", configuration = FeignConfig.class)
public interface ControllerFromUsers {

    @Operation(summary = "Get Statistic",
            description = "Позволяет получить статистику по кол-ву" +
                    " регистраций по возрастам и по кол-ву регистраций" +
                    " по месяцам за указанный промежуток времени", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account/statistic",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AccountStatisticRequestDto> getListAllAccounts();


    //Получение постов
    @GetMapping(value = "/api/v1/post", produces = MediaType.APPLICATION_JSON_VALUE)
//    ResponseEntity<Void> postsGet(PostSearchDto postSearch, Pageable pageable);

    @Operation(summary = "create Account", description = "Создание аккаунта при регистрации", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account",
            method = RequestMethod.POST)
    ResponseEntity<AccountDto> createAccount();


    @Operation(summary = "Block/unblock user",
            description = "блокировка / разблокировка пользователя", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account/is-block",
            method = RequestMethod.PUT)
    ResponseEntity<Void> blockUser(@RequestParam(value = "id") Long id);


    @Operation(summary = "create Account", description = "Создание аккаунта при регистрации", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PostMapping(value = "/api/v1/account",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AccountDto> createAccount(@RequestBody User user);


    @Operation(summary = "Delete account by id", description = "Полность удаляет аккаунт из базы по id", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account/{id}",
            method = RequestMethod.DELETE)
    ResponseEntity<String> deleteAccountById();


    @Operation(summary = "Edit Account", description = "Обновление данных аккаунта", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account",
            method = RequestMethod.PUT)
    ResponseEntity<AccountDto> editAccount();

    @Operation(summary = "Search users by name",
            description = "Поиск пользователей по имени", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account/search/{username}",
            method = RequestMethod.GET)
    ResponseEntity<List<User>> getUserByName(@PathVariable(value = "username") String username);


    @Operation(summary = "all users count",
            description = "всего пользователей", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account/all-users",
            method = RequestMethod.GET)
    long getAllUsersCount();

    @Operation(summary = "admin list",
            description = "лист админов", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account/all-admins",
            method = RequestMethod.GET)
    ResponseEntity<List<AccountDto>> getAllAdmins();

    @Operation(summary = "Get account by id", description = "Получение данных по id", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account/{id}",
            method = RequestMethod.GET)
    ResponseEntity<AccountDto> getAccountById();

    @Operation(summary = "edit account if login", description = "Обновление авторизованного аккаунта", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account/me",
            method = RequestMethod.PUT)
    ResponseEntity<AccountDto> editAccountIfLogin();
}
