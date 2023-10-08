package ru.skillbox.group39.telegrambot.service.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.skillbox.group39.telegrambot.dto.authenticate.AuthenticateDto;
import ru.skillbox.group39.telegrambot.dto.authenticate.AuthenticateResponseDto;
import ru.skillbox.group39.telegrambot.feign.AuthorizationController;

@Component
public class AuthorizationAction {

    AuthorizationController authorizationService;
    public AuthenticateDto authenticateDto;
    public AuthenticateResponseDto authenticateResponseDto;

    ObjectMapper objectMapper;

    public AuthorizationAction(AuthorizationController authorizationService) {
        objectMapper = new ObjectMapper();
        this.authorizationService = authorizationService;
        authenticateDto = new AuthenticateDto();
        authenticateResponseDto = new AuthenticateResponseDto();
    }

    public boolean isValid() {
        Object response;
        try {
            response = authorizationService.login(authenticateDto);
        } catch (Exception e) {
            return false;
        }
        authenticateResponseDto = objectMapper.convertValue(response, AuthenticateResponseDto.class);
        System.out.println(response.toString());
        return true;
    }

    public String getToken() {
        return authenticateResponseDto.getAccessToken();
    }
}
