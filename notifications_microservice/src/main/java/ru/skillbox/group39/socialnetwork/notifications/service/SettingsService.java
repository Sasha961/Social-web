package ru.skillbox.group39.socialnetwork.notifications.service;

import ru.skillbox.group39.socialnetwork.notifications.dto.setting.SettingChangeDto;

/**
 * @author Artem Lebedev | 16/09/2023 - 21:53
 */
public interface SettingsService {

	Object getSettings();
	Object createSettings(Long userId);
	Object changeSetting(SettingChangeDto settingChangeDto);

}
