package ru.skillbox.group39.socialnetwork.notifications.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.group39.socialnetwork.notifications.model.settings.SettingsModel;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * @author Artem Lebedev | 16/09/2023 - 22:39
 */

public interface SettingsRepository extends JpaRepository<SettingsModel, Long> {

	Optional<SettingsModel> findByUserId(Long userId);

	@Modifying
	@Query(value = "update notifications_settings set :typeKey=:typeValue where ns.id = :id", nativeQuery = true)
	void updateSetting(@Param(value = "typeKey") String typeKey, @Param(value = "typeValue") String typeValue, @Param(value = "id") Long id);

}
