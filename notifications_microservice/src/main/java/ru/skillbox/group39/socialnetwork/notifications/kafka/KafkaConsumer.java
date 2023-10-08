package ru.skillbox.group39.socialnetwork.notifications.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.skillbox.group39.socialnetwork.notifications.dto.notify.common.NotificationCommonDto;
import ru.skillbox.group39.socialnetwork.notifications.repositories.NotificationSimpleRepository;
import ru.skillbox.group39.socialnetwork.notifications.service.NotificationService;

import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

	private final NotificationSimpleRepository notificationSimpleRepository;
	private final ObjectMapper objectMapper;
	private final NotificationService notificationService;

	@Value(value = "${spring.application.name}")
	private String groupId;
	@Value(value = "${kafka.topics.notify}")
	private String notifyTopic;
	@Value(value = "${kafka.topics.notify-common}")
	private String notifyCommonTopic;

	@Transactional
	@KafkaListener(topics = "notify-topic", groupId = "${spring.application.name}", containerFactory = "kafkaListenerContainerFactory")
	public void listenGroupAuth(@NotNull String message) {
		log.info("--|< message '{}' received from group '{}' from topic '{}'", message, groupId, notifyTopic);
	}

	@KafkaListener(topics = "${kafka.topics.notify-common}", groupId = "${spring.application.name}", containerFactory = "kafkaListenerContainerFactory")
	public void listenGroupNotify(String message) {
		NotificationCommonDto notificationCommonDto;
		try {
			notificationCommonDto = objectMapper.readValue(message, NotificationCommonDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		log.info("--|< Message data received from group '{}' from topic '{}': {}", groupId, notifyCommonTopic, notificationCommonDto);

		notificationService.processCommonNotification(notificationCommonDto);

	}
}
