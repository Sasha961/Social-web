package ru.skillbox.group39.socialnetwork.notifications.dto.event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.group39.socialnetwork.notifications.dto.notify.ENotificationType;

import java.sql.Timestamp;

/**
 * @author Artem Lebedev | 24/08/2023 - 17:46 <p>
 * @Description<p>
 * Dto получения уведомлений о событии <p>
 * producerId - автор события <p>
 * receiverId - получатель события <p>
 * notificationType - тип уведомления <p>
 * content - описание события <p>
 * timestamp - время создания
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventNotificationDto {

    private Long producerId;
    private Long consumerId;
    private String content;
    private ENotificationType notificationType;
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
}
