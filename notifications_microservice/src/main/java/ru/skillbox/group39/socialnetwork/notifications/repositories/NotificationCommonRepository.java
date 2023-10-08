package ru.skillbox.group39.socialnetwork.notifications.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.group39.socialnetwork.notifications.model.notification.NotificationCommonModel;

/**
 * @author Artem Lebedev | 07/09/2023 - 08:24
 */
@Repository
public interface NotificationCommonRepository extends JpaRepository<NotificationCommonModel, Long> {
}
