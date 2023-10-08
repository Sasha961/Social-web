package ru.skillbox.group39.socialnetwork.authorization.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessage {
	private String timestamp;
	private int code;
	private int status;
	private String error;
	private String message;
	private String path;
	private String requestId;

	@Override
	public String toString() {
		return String.format("ExceptionMessage " +
						"[timestamp = %s, " +
						"code = %d, " +
						"status = %d, " +
						"error = %s, " +
						"message = %s, " +
						"path = %s, " +
						"requestId = %s]",
				timestamp,
				code,
				status,
				error,
				message,
				path,
				requestId);
	}
}
