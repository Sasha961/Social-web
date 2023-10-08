package ru.skillbox.group39.socialnetwork.notifications.dto.notify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Artem Lebedev | 24/08/2023 - 17:46 <p>
 * description - "проштампованное" событие <p><p>
 * timeStamp - время отправки события <p>
 * data - dto NotificationSimpleDto. <p>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationStampedDto {

    private Timestamp timeStamp;
    private NotificationSimpleDto data;

    @Override
    public String toString() {
        return "\nStamped notification object" +
                "\n{" +
                "\n Timestamp:              '" + timeStamp + '\'' +
                "\n Data:" +
                "\n {" +
                "\n     id:                 '" + data.getId() + '\'' +
                "\n     Author:"+
                "\n     {" +
                "\n         id:             '"+ data.getAuthor().getId() + '\'' +
                "\n         First name:     '"+ data.getAuthor().getFirstName() + '\'' +
                "\n         Last name:      '"+ data.getAuthor().getLastName() + '\'' +
                "\n         Photo url:      '"+ data.getAuthor().getPhoto() + '\'' +
                "\n     }" +
                "\n     Notification type:  '" + data.getNotificationType() + '\'' +
                "\n     Timestamp:          '" + data.getTimestamp() + '\'' +
                "\n     Content:            '" + data.getContent() + '\'' +
                "\n}";
    }
}
