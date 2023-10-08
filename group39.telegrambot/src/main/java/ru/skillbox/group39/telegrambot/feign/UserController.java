package ru.skillbox.group39.telegrambot.feign;

import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.group39.telegrambot.config.FeignConfig;
import ru.skillbox.group39.telegrambot.dto.account.AccountDto;

@FeignClient(name = "Users", url = "http://5.63.154.191:8085", configuration = FeignConfig.class)
public interface UserController {
    @GetMapping(value = "/api/v1/account/me")
    AccountDto getAccountWhenLogin(@RequestHeader("Authorization") @NonNull String bearerToken);

    @RequestMapping(value = "/api/v1/{id}/account",
            method = RequestMethod.GET)
    AccountDto getAccountById(@RequestHeader("Authorization") @NonNull String bearerToken, @PathVariable Long id);

}
