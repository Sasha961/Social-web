package com.friends.demo.controller.feignClient;

import com.friends.demo.config.FeignConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Users", url = "http://5.63.154.191:8085", configuration = FeignConfig.class)
public interface UsersController {

//    @Operation(summary = "Get account by id", description = "Получение данных по id", tags = {"Account service"})
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successful operation"),
//            @ApiResponse(responseCode = "400", description = "Bad request"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized")})
//    @RequestMapping(value = "/api/v1/account/{id}",
//            method = RequestMethod.GET)
//    ResponseEntity<AccountDto> getAccountById();

}
