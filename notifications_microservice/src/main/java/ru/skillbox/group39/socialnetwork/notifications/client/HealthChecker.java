package ru.skillbox.group39.socialnetwork.notifications.client;

import lombok.extern.slf4j.Slf4j;
import ru.skillbox.group39.socialnetwork.notifications.exception.exceptions.ConnectException;

import java.net.Socket;

/**
 * @author Artem Lebedev | 03/09/2023 - 21:59
 * @Description
 * To prevent HTTP status 500 if the user service is not available,
 * and to send an adequate message to the front, when contacting via FeignClient,
 * an available/unavailable check is performed
 */

@Slf4j
public class HealthChecker {

	public static void checkHealthyService(String host, Integer port) {
		try {
			Socket clientSocket = new Socket(host, port);
			log.info(" * Host '{}:{}' available", host, port);
			clientSocket.close();
		} catch (ConnectException e) {
			log.info(" * Host and port combination invalid. {}", e.getMessage());
		} catch (Exception e) {
			throw new ConnectException(String.format("Host %s:%d not available", host, port));
		}
	}
}
