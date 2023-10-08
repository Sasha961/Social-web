package ru.skillbox.group39.telegrambot.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.skillbox.group39.telegrambot.config.FeignConfig;
import ru.skillbox.group39.telegrambot.dto.authenticate.AuthenticateDto;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "Auth", url = "http://5.63.154.191:8084/api/v1/auth", configuration = FeignConfig.class)
public interface AuthorizationController {

    @RequestMapping(
            value = "/login",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    Object login(@RequestBody AuthenticateDto request);
}

