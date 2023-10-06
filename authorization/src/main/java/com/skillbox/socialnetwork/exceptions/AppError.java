package com.skillbox.socialnetwork.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppError {
	private int statusCode;
	private String message;
	private Date timestamp;

	public AppError(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
		this.timestamp = new Date();
	}
}
