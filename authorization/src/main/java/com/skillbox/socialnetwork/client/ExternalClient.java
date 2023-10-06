package com.skillbox.socialnetwork.client;

import com.skillbox.socialnetwork.dto.RegistrationDto;
import com.skillbox.socialnetwork.dto.user.UserResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "users-service", url = "${users-service.url}")
public interface ExternalClient {

	String AUTH_TOKEN = "Authorization";

	@CircuitBreaker(name = "user-service-breaker")
	@Retry(name = "user-service-retry")
	@GetMapping(value = "/account", produces = {"application/json"})
	UserResponseDto getUserDetails(@RequestParam String email);

	@CircuitBreaker(name = "user-service-breaker")
	@Retry(name = "user-service-retry")
	@PostMapping(value = "/account", produces = {"application/json"}, consumes = {"application/json"})
	void createUser(@RequestBody RegistrationDto registrationDto);


	/*
	example

	@GetMapping(value = "/account")
	@Headers("Content-Type: application/json")
	RestTemplate getUserFullDetails(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam String email);

		ResponseEntity<String> account(@RequestHeader("Authorization") @NonNull String authHeader, @RequestParam @NonNull String email) {
		String[] parts = authHeader.split(" ");
		String bearerToken = parts[1];
		log.info("Bearer token received. Token -> " + bearerToken);
	 */

}
