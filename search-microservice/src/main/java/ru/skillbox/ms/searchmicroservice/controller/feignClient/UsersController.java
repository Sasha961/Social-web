package ru.skillbox.ms.searchmicroservice.controller.feignClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.skillbox.ms.searchmicroservice.config.FeignConfig;

import java.util.List;

@FeignClient(name = "AccountController", url = "http://localhost:8085", configuration = FeignConfig.class)
public interface UsersController {
    @Operation(summary = "Search users by name",
            description = "Поиск пользователей по имени", tags = {"Account service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @RequestMapping(value = "/api/v1/account/search/{username}",
            method = RequestMethod.GET)
    ResponseEntity<List<AcccountDto>> getUserByName(@PathVariable(value = "username") String username);

}
