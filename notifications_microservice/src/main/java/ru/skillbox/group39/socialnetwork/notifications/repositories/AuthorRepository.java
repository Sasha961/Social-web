package ru.skillbox.group39.socialnetwork.notifications.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.group39.socialnetwork.notifications.model.notification.AuthorModel;

/**
 * @author Artem Lebedev | 14/09/2023 - 15:26
 */
public interface AuthorRepository extends JpaRepository<AuthorModel, Long> {

}
