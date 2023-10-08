package ru.skillbox.group39.socialnetwork.notifications.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skillbox.group39.socialnetwork.notifications.client.config.FeignSupportConfig;
import ru.skillbox.group39.socialnetwork.notifications.client.dto.AccountDto;

import java.util.List;

@FeignClient(name = "friends-service", url = "${gtw.friends-service}", configuration = FeignSupportConfig.class)
public interface FriendsClient {

	@GetMapping(
			value = "/friendId/{userId}",
			produces = {"application/json"})
	List<Long> getFriendsListById(@PathVariable Long userId);
}
