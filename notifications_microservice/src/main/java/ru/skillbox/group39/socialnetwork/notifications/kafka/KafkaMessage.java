package ru.skillbox.group39.socialnetwork.notifications.kafka;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class KafkaMessage {

	private long number;
	private String message;
	private Date timestamp;

}

