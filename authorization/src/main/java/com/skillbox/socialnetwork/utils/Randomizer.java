package com.skillbox.socialnetwork.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class Randomizer {
	public String getRandomString(Integer length, Boolean useLetters, Boolean useNumbers) {

//		int length = 10;
//		boolean useLetters = true;
//		boolean useNumbers = false;
		return RandomStringUtils.random(length, useLetters, useNumbers);
	}
}
