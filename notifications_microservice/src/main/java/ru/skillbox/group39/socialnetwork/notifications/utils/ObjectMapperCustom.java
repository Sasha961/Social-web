package ru.skillbox.group39.socialnetwork.notifications.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.skillbox.group39.socialnetwork.notifications.dto.notify.common.NotificationCommonDto;

import java.io.IOException;

/**
 * @author Artem Lebedev | 14/09/2023 - 21:54
 */

@Slf4j
@Component
public class ObjectMapperCustom {
	private static final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

	public static @NotNull ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.addHandler(new DeserializationProblemHandler() {

			@Override
			public boolean handleUnknownProperty(DeserializationContext ctxt,
			                                     JsonParser jp, JsonDeserializer<?> deserializer,
			                                     Object beanOrClass, String propertyName) {

				String unknownField = String.format(" ! Ignoring unknown property %s while deserializing %s", propertyName, beanOrClass);
				log.error(getClass().getSimpleName(), unknownField);
				return true;
			}
		});

		return objectMapper;
	}

	/**
	 * @param anyDto any dto with field's name as on NotificationStampedDto
	 * @return NotificationCommonDto
	 */
	public static <T> @NotNull NotificationCommonDto mapAnyToCommonNotificationDto(T anyDto) {
		try {
			return objectMapper().readValue(objectWriter.writeValueAsString(anyDto), NotificationCommonDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
