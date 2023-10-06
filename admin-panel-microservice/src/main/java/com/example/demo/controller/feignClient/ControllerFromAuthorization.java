package com.example.demo.controller.feignClient;

import com.example.demo.dto.account.RegistrationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

public interface ControllerFromAuthorization {

    @Operation(summary = "", description = "Регистрация нового пользователя", tags = {"Authorization service"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    @RequestMapping(value = "/register",
            consumes = {"application/json"},
            method = RequestMethod.POST)
    void register(@Valid
                  @Parameter(
                          in = ParameterIn.DEFAULT,
                          description = "id, email, password1, password2, firstname, lastname, captcha code, captcha secret",
                          required = true,
                          schema = @Schema())
                  @RequestBody RegistrationDto body);
}
