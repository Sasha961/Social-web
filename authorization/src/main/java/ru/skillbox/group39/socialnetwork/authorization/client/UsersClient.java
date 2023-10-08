package ru.skillbox.group39.socialnetwork.authorization.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.group39.socialnetwork.authorization.client.config.FeignSupportConfig;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.RegistrationDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.user.AccountDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.user.AccountSecureDto;

/**
 * Below two options of working with Users micro service:
 * - direct;
 * - thought Gateway
 */

@FeignClient(name = "users-service", url = "${gtw.users-service}", configuration = FeignSupportConfig.class)
//@FeignClient(name = "users-service", url = "${users-service.url}", configuration = FeignSupportConfig.class)
public interface UsersClient {

	@CircuitBreaker(name = "user-service-breaker")
	@Retry(name = "user-service-retry")
	@GetMapping(
			value = "/account",
			produces = {"application/json"})
	AccountDto getUserDetails(@RequestParam String email);

	@CircuitBreaker(name = "user-service-breaker")
	@Retry(name = "user-service-retry")
	@PostMapping(
			value = "/account",
			produces = {"application/json"},
			consumes = {"application/json"})
	AccountSecureDto createUser(@RequestBody RegistrationDto registrationDto);
}
