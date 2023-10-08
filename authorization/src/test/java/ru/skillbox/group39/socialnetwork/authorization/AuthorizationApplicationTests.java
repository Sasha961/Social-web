package ru.skillbox.group39.socialnetwork.authorization;

import ru.skillbox.group39.socialnetwork.authorization.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorizationApplicationTests {

	@Autowired
	private AuthController authController;

	@Test
	public void contextLoads() throws Exception {
	}
}
