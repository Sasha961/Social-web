package ru.skillbox.ms.searchmicroservice.controller.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import ru.skillbox.ms.searchmicroservice.config.FeignConfig;

@FeignClient(name = "AccountController", url = "http://localhost:8085", configuration = FeignConfig.class)
public interface PostsController {


}
