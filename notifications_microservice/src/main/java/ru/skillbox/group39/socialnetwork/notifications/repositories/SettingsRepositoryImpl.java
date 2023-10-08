package ru.skillbox.group39.socialnetwork.notifications.repositories;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Artem Lebedev | 17/09/2023 - 14:35
 */

@RequiredArgsConstructor
public class SettingsRepositoryImpl{
	private final JdbcTemplate jdbcTemplate;


	@Modifying
	public void updateSetting(Long id, String typeKey, Boolean typeValue) {
		String snakeKey = toSnakeCase(typeKey);
		String sql = "UPDATE notifications_settings SET " + "\"" + snakeKey + "\"" + " = ? WHERE id = ?";
		jdbcTemplate.update(sql, typeValue, id);
	}

	private @NotNull String toSnakeCase(@NotNull String camelCase) {
		return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
	}
}
