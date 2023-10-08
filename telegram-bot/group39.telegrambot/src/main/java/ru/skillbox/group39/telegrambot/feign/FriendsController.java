package ru.skillbox.group39.telegrambot.feign;

import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.skillbox.group39.telegrambot.config.FeignConfig;

import java.util.List;


@FeignClient(name = "Friends", url = "http://5.63.154.191:8083/api/v1/friends", configuration = FeignConfig.class)
public interface FriendsController {

    @GetMapping("/friendId/{id}")
    List<Long> getFriendById(@RequestHeader("Authorization") @NonNull String bearerToken, @PathVariable(name = "id") Long id);
}

