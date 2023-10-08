package ru.skillbox.group39.socialnetwork.authorization.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.group39.socialnetwork.authorization.kafka.KafkaProducer;
import ru.skillbox.group39.socialnetwork.authorization.service.AuthService;
import ru.skillbox.group39.socialnetwork.authorization.exception.ErrorResponse;
import ru.skillbox.group39.socialnetwork.authorization.security.jwt.JwtTokenUtils;
import ru.skillbox.group39.socialnetwork.authorization.service.UserService;

/**
 * @author Artem Lebedev | 06/09/2023 - 0:43
 */

@Slf4j
@Tag(name = "Authorization debug", description = "The Authorization Debug Controller")
@RestController
@Getter
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class DebuggingController {

	private final AuthService authService;
	private final UserService userService;
	private final JwtTokenUtils jwtTokenUtils;
	private final AuthenticationManager authenticationManager;
	private final KafkaProducer kafkaProducer;

	public Object getClaims(@RequestHeader("Authorization") @NonNull String bearerToken) {
		final String[] parts = bearerToken.split(" ");
		final String jwtToken = parts[1];
		final Boolean result = jwtTokenUtils.isJwtTokenIsNotExpired(jwtToken);
		if (result) {
			return jwtTokenUtils.getAllClaimsFromToken(jwtToken);

		}
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNAUTHORIZED, ""), HttpStatus.UNAUTHORIZED);
	}

	public Object getUserNameFromToken(@NonNull String bearerToken) {
		return jwtTokenUtils.getUsername(bearerToken.substring(7));
	}

	public void sendMessage(String topicName, String message) {
		kafkaProducer.sendMessage(topicName, message);
	}

	@RequestMapping(value = "/username2", method = RequestMethod.GET)
	@ResponseBody
	public String currentUserName() {
		return null;
	}

	@GetMapping("/response-entity-builder-with-http-headers")
	public ResponseEntity<String> usingResponseEntityBuilderAndHttpHeaders() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Baeldung-Example-Header",
				"Value-ResponseEntityBuilderWithHttpHeaders");

		return ResponseEntity.ok()
				.headers(responseHeaders)
				.body("Response with header using ResponseEntity");
	}
}
