package ru.skillbox.group39.socialnetwork.notifications.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.skillbox.group39.socialnetwork.notifications.model.notification.NotificationStampedModel;

import java.util.List;

/**
 * @author Artem Lebedev | 13/09/2023 - 00:30
 */
public interface NotificationStampedRepository extends JpaRepository<NotificationStampedModel, Long>, PagingAndSortingRepository<NotificationStampedModel,Long> {

//	@Query("select n from NotificationStampedModel n")
	@NotNull Page<NotificationStampedModel> findAll(@NotNull Pageable pageable);
	Page<NotificationStampedModel> findAllByData_ConsumerId(Long consumerId, Pageable pageable);
	List<NotificationStampedModel> findAllByData_ConsumerId(Long consumerId);
	Long countByData_ConsumerId(Long consumerId);
}
