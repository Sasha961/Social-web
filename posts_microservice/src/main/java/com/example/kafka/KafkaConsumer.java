package com.example.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

	@Value(value = "${spring.application.name}")
	private String groupId;

	private final String KEYWORD_FOR_CONSUMER = "WORLD";
	private final ObjectMapper objectMapper;
	private final ModelMapper modelMapper;


	@KafkaListener(topics = "${kafka.topics.posts}", groupId = "${spring.application.name}", containerFactory = "kafkaListenerContainerFactory")
	public void listenGroupPosts(String message) {
		log.info("--|< Message {} data received from group '{}'", message, groupId);
	}
}
