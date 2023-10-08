package ru.skillbox.group39.socialnetwork.authorization.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.skillbox.group39.socialnetwork.authorization.model.Registration;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.RegistrationDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

	@Value(value = "${spring.application.name}")
	private String groupId;

	private final String KEYWORD_FOR_CONSUMER = "WORLD";
	private final ObjectMapper objectMapper;
	private final ModelMapper modelMapper;


	@KafkaListener(topics = "${kafka.topics.auth}", groupId = "${spring.application.name}", containerFactory = "kafkaListenerContainerFactory")
	public void listenGroupAuth(String message) {
		RegistrationDto registrationDto;
		try {
			registrationDto = objectMapper.readValue(message, RegistrationDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		log.info(" * Message data received from group '{}'", groupId);
		Registration registration = modelMapper.map(registrationDto, Registration.class);
		log.info("--|< RegistrationDto received and map to Registration entity. {}", registration.toString());
	}

	@KafkaListener(topics = "${kafka.topics.notify}", groupId = "${spring.application.name}", containerFactory = "kafkaListenerContainerFactory")
	public void listenGroupNotify(String message) {
		log.info(" * Message {} data received from group '{}'", message, groupId);
	}

	/**
	 * Filtered message consumer
	 */

//	@KafkaListener(topics = "${kafka.topics.auth}", groupId = "${spring.application.name}", containerFactory = "filteredKafkaListenerContainerFactory")
//	public void listenFilteredMessage(String message) {
//		log.info("--|< message '{}' received from group '{}' filtered by KEYWORD '{}'", message, groupId, KEYWORD_FOR_CONSUMER);
//	}

}
