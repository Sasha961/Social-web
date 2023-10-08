package ru.skillbox.group39.socialnetwork.notifications.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.group39.socialnetwork.notifications.model.notification.NotificationSimpleModel;

import java.util.Optional;


@Repository
public interface NotificationSimpleRepository extends JpaRepository<NotificationSimpleModel, Long>, PagingAndSortingRepository<NotificationSimpleModel,Long> {

	NotificationSimpleModel findByProducerId(Long producerId);

	@Query("select n from NotificationSimpleModel n")
	@NotNull Page<NotificationSimpleModel> findAll(@NotNull Pageable pageable);

	@NotNull Optional<NotificationSimpleModel> findById(@NotNull Long id);
}
