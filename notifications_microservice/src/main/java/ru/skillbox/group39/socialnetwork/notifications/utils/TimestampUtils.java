package ru.skillbox.group39.socialnetwork.notifications.utils;

import java.sql.Timestamp;

/**
 * @author Artem Lebedev | 14/09/2023 - 09:14
 */

public class TimestampUtils {

	public static Timestamp NOW() {
		return new Timestamp(System.currentTimeMillis());
	}
}
