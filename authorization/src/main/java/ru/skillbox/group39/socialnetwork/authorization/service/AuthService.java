package ru.skillbox.group39.socialnetwork.authorization.service;


import org.springframework.http.ResponseEntity;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.RegistrationDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateResponseDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.password.NewPasswordDto;

public interface AuthService {

	ResponseEntity<?> login(AuthenticateDto authenticateDto);
	ResponseEntity<?> createUser(RegistrationDto registrationDto);
	ResponseEntity<?> refreshTokens(AuthenticateResponseDto authenticateResponseDto);
	ResponseEntity<?> doCaptcha(String captchaCode);
	ResponseEntity<?> setNewPassword(String linkId, NewPasswordDto newPasswordDto);
}
