package ru.skillbox.group39.socialnetwork.notifications.dto.notify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Artem Lebedev | 24/08/2023 - 19:30 <p>
 * description - dto события доп. <p><p>
 * author - DTO AuthorDto <p>
 * content - описание события <p>
 * notificationType - тип уведомления <p>
 * timestamp - время события
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationSimpleDto {

	private Long id;
	private AuthorDto author;
	private ENotificationType notificationType;
	private Timestamp timestamp;
	private String content;

	@Override
	public String toString() {
		return "\nSimple notification object" +
				"\n{" +
				"\n     id:                  '" + id + '\'' +
				"\n     Author:              '" + author + '\'' +
				"\n         {" +
				"\n                 id:          '"+ author.getId() + '\'' +
				"\n                 First name:  '"+ author.getFirstName() + '\'' +
				"\n                 Last name:   '"+ author.getLastName() + '\'' +
				"\n                 Photo url:   '"+ author.getPhoto() + '\'' +
				"\n         }" +
				"\n     Notification type:   '" + notificationType + '\'' +
				"\n     Timestamp:           '" + timestamp + '\'' +
				"\n     Content:             '" + content + '\'' +
				"\n}";
	}
}
