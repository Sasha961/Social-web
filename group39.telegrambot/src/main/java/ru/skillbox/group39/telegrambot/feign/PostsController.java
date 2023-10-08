package ru.skillbox.group39.telegrambot.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.group39.telegrambot.config.FeignConfig;
import ru.skillbox.group39.telegrambot.config.RestResponsePage;
import ru.skillbox.group39.telegrambot.dto.posts.Post;

@FeignClient(name = "Posts", url = "http://5.63.154.191:8089/api/v1/post", configuration = FeignConfig.class)
public interface PostsController {

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    RestResponsePage<Post> postsGet(
//            @RequestHeader("Authorization") @NonNull String bearerToken,
            @RequestParam(name = "accountIds") Long id);

}

