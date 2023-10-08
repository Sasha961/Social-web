package ru.skillbox.group39.search.controller.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.group39.search.config.FeignConfig;
import ru.skillbox.group39.search.dto.account.AccountDto;

import java.util.List;

@FeignClient(name = "users-ms", url = "http://5.63.154.191:8088/users/account", configuration = FeignConfig.class)
public interface UsersController {

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    List<AccountDto> getUserByName(@RequestParam(name = "username") String username,
                                   @RequestParam(name = "offset", defaultValue = "0") String offset,
                                   @RequestParam(name = "limit", defaultValue = "3") String limit);
}
