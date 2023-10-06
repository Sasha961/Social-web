package com.skillbox.socialnetwork.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class AuthResponses {
	String refreshResponseOK = "Tokens refreshed successfully";
	String registrationResponseOK = "You are registered successfully";

}
