package com.skillbox.socialnetwork.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloFromAuthController {
	@GetMapping("/")
	public String hello() {
		return "Hello, User!" +
				"\n I'm authorization micro service." +
				"\n If you wanna to speak with me - learn REST API";
	}
}
