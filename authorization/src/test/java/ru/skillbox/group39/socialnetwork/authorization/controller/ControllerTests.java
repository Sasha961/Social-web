package ru.skillbox.group39.socialnetwork.authorization.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skillbox.group39.socialnetwork.authorization.controller.Impl.AuthControllerImpl;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateResponseDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.RegistrationDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.password.NewPasswordDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.password.PasswordRecoveryDto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith({MockitoExtension.class})
public class ControllerTests {

	@Mock
	AuthControllerImpl authController;



	@Test
	void register() {
		RegistrationDto registrationDto = new RegistrationDto("foo", "bar", "foo@bar.com", "raboof");
		ResponseEntity<?> response = new ResponseEntity<>(new Object(), HttpStatus.OK);
		doReturn(response).when(this.authController).register(registrationDto);

		ResponseEntity<?> responseEntity = (ResponseEntity<?>) this.authController.register(registrationDto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void captcha() {
		doReturn(new ResponseEntity<String>(HttpStatus.OK)).when(this.authController).captcha();

		ResponseEntity<?> responseEntity = (ResponseEntity<?>) this.authController.captcha();

		assertNotNull(responseEntity);
	}

	@Test
	void login() {
		AuthenticateDto authenticateDto = new AuthenticateDto("foo@bar.com", "raboof");
		doReturn(new ResponseEntity<>(HttpStatus.OK)).when(this.authController).login(authenticateDto);

		ResponseEntity<?> responseEntity = (ResponseEntity<?>) this.authController.login(authenticateDto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void refresh() {
		AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto("test_token", "test_token_2");
		doReturn(new ResponseEntity<>(HttpStatus.OK)).when(this.authController).refresh(authenticateResponseDto);

		ResponseEntity<?> responseEntity = (ResponseEntity<?>) this.authController.refresh(authenticateResponseDto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void recoveryPasswordByEmail() {
		PasswordRecoveryDto passwordRecoveryDto = new PasswordRecoveryDto("foo@bar.com");
		doReturn(new ResponseEntity<>(HttpStatus.OK)).when(this.authController).recoveryPasswordByEmail(passwordRecoveryDto);

		ResponseEntity<?> responseEntity = this.authController.recoveryPasswordByEmail(passwordRecoveryDto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void setNewPassword() {
		NewPasswordDto newPasswordDto = new NewPasswordDto("foobar");
		doReturn(new ResponseEntity<>(HttpStatus.OK)).when(this.authController).setNewPassword("link", newPasswordDto);

		ResponseEntity<?> responseEntity = (ResponseEntity<?>) this.authController.setNewPassword("link", newPasswordDto);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}
