package ru.skillbox.group39.friends.service.notificator;

import com.demo.storage.notifications.ENotificationType;
import com.demo.storage.notifications.EServiceName;
import com.demo.storage.notifications.NotificationCommonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.skillbox.group39.friends.kafka.KafkaProducer;

/**
 * @author Artem Lebedev | 27/09/2023 - 22:55
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class NotificatorProcessor {
	private final KafkaProducer kafkaProducer;
	public void friendRequestNotificator(Long id, Long friendId) {
		NotificationCommonDto friendRequestNotify = NotificationCommonDto.builder()
				.consumerId(friendId)
				.notificationType(ENotificationType.FRIEND_REQUEST)
				.service(EServiceName.FRIENDS)
				.producerId(id)
				.build();
		kafkaProducer.produceKafkaMessage("notify-topic-common", friendRequestNotify);
	}
}
