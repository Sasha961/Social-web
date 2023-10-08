package ru.skillbox.group39.telegrambot.service.actions;

import org.springframework.stereotype.Component;
import ru.skillbox.group39.telegrambot.feign.UserController;

@Component
public class MyProfileAction {

    private UserController userService;

    public MyProfileAction(UserController userService) {
        this.userService = userService;
    }

    public String getMyProfile(String token) {
        try {
            return userService.getAccountWhenLogin("Bearer " + token).toString();
        } catch (Exception e) {
            return "Unauthorized";
        }
    }

}
