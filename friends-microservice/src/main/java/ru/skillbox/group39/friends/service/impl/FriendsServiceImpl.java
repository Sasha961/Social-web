package ru.skillbox.group39.friends.service.impl;

import com.demo.storage.notifications.NotificationCommonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import ru.skillbox.group39.friends.dto.friend.FriendShortDto;
import ru.skillbox.group39.friends.dto.enums.StatusCode;
import ru.skillbox.group39.friends.dto.CountDto;
import ru.skillbox.group39.friends.globalHendler.ResourceNotFoundException;
import ru.skillbox.group39.friends.model.Status;
import ru.skillbox.group39.friends.model.Users;
import ru.skillbox.group39.friends.repository.StatusRepository;
import ru.skillbox.group39.friends.repository.UserRepository;
import ru.skillbox.group39.friends.service.FriendsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.group39.friends.service.notificator.NotificatorProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static ru.skillbox.group39.friends.dto.enums.StatusCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendsServiceImpl implements FriendsService {
	private static final String ERROR_MESSAGE_STATUS_NOT_FOUND = "status not found: ";
	private static final String ERROR_MESSAGE_USER_NOT_FOUND = "user not found id: ";
	private static final String ERROR_MESSAGE_ID_NOT_FOUND = "В токене отстутствует параметр с ключом \"userId\" ";
	private static final String ERROR_MESSAGE_CHECK_MAIN_ID = "нельзя посылать запрос самому себе";

	private final UserRepository userRepository;
	private final StatusRepository statusRepository;
    private final NotificatorProcessor notificatorProcessor;

	private static Long mainId;

	@Override
	public FriendShortDto approvedFriend(Long id) {
		checkUserIdAndMainId(id);
		checkMainIdNotEqualsId(id);
		Status usersStatusToWhom = getStatusOrElseThrow(mainId, id);
		Status usersStatusFromWhom = getStatusOrElseThrow(id, mainId);

		if (!usersStatusToWhom.getStatus().equals(BLOCKED) && (usersStatusFromWhom.getStatus().equals(REQUEST_TO)
				|| usersStatusFromWhom.getStatus().equals(SUBSCRIBED))) {
			changeStatus(usersStatusToWhom, FRIEND);
			changeStatus(usersStatusFromWhom, FRIEND);
		}
		return createFriendShortDto(
				id,
				false,
				usersStatusToWhom.getPreviousStatusCode(),
				usersStatusToWhom.getStatus(),
				0);
	}

	@Override
	public FriendShortDto unBlock(Long id) {
		checkUserIdAndMainId(id);
		checkMainIdNotEqualsId(id);
		Status usersStatus = getStatusOrElseThrow(mainId, id);

		if (usersStatus.getStatus().equals(BLOCKED)) {
			changeStatus(usersStatus, NONE);
		}
		return createFriendShortDto(
				id,
				false,
				usersStatus.getPreviousStatusCode(),
				usersStatus.getStatus(),
				0);
	}

	@Override
	public FriendShortDto block(Long id) {
		checkMainIdNotEqualsId(id);
		checkUserIdAndMainId(id);
		Status usersStatusTo = checkStatusIfExistOrCreate(mainId, id, NONE);
		Status usersStatusFrom = checkStatusIfExistOrCreate(id, mainId, NONE);

		if (usersStatusFrom.getStatus().equals(FRIEND)) {
			changeStatus(usersStatusFrom, REQUEST_TO);
		}
		if (!usersStatusTo.getStatus().equals(BLOCKED)) {
			changeStatus(usersStatusTo, BLOCKED);
		}
		return createFriendShortDto(
				id,
				false,
				usersStatusTo.getPreviousStatusCode(),
				usersStatusTo.getStatus(),
				0);
	}

	@Override
	public FriendShortDto requestFriend(Long id) {
		checkUserIdAndMainId(id);
		checkMainIdNotEqualsId(id);
		Status usersStatusToWhom = checkStatusIfExistOrCreate(mainId, id, REQUEST_TO);
		Status usersStatusFromWhom = checkStatusIfExistOrCreate(id, mainId, REQUEST_FROM);

		if (usersStatusFromWhom.getStatus().equals(BLOCKED)) {
			changeStatus(usersStatusToWhom, SUBSCRIBED);
		} else if (!usersStatusToWhom.getStatus().equals(REQUEST_TO) &&
				!usersStatusToWhom.getStatus().equals(FRIEND)) {
			changeStatus(usersStatusToWhom, REQUEST_TO);
		} else if (!usersStatusFromWhom.getStatus().equals(REQUEST_FROM) &&
				!usersStatusToWhom.getStatus().equals(FRIEND)) {
			changeStatus(usersStatusToWhom, REQUEST_FROM);
		}

		if (usersStatusToWhom.getStatus().equals(REQUEST_TO)
				&& usersStatusFromWhom.getStatus().equals(REQUEST_TO)) {
			changeStatus(usersStatusToWhom, FRIEND);
			changeStatus(usersStatusFromWhom, FRIEND);
		} else {
			saveStatus(usersStatusFromWhom);
			saveStatus(usersStatusToWhom);
		}

		return createFriendShortDto(
				id,
				false,
				usersStatusToWhom.getPreviousStatusCode(),
				usersStatusToWhom.getStatus(),
				0);
	}

	@Override
	public FriendShortDto subscribe(Long id) {
		checkUserIdAndMainId(id);
		checkMainIdNotEqualsId(id);
		Status usersStatus = checkStatusIfExistOrCreate(mainId, id, NONE);

		if (!usersStatus.getStatus().equals(SUBSCRIBED)) {
			changeStatus(usersStatus, SUBSCRIBED);
		}
		saveStatus(usersStatus);
		return createFriendShortDto(
				id,
				false,
				usersStatus.getPreviousStatusCode(),
				usersStatus.getStatus(),
				0);
	}

	@Override
	public List<FriendShortDto> getAll(Integer size, String code, String statusCode, Integer ageFrom, Integer ageTo) {
		List<Long> usersId = statusRepository
				.getUserListByStatusFriend(mainId, statusCode, PageRequest.of(0, size))
				.stream()
				.limit(size)
				.collect(Collectors.toList());

		return usersId.stream()
				.map(u -> statusRepository.findByUserToAndUserFrom(mainId, u))
				.map(u -> FriendShortDto.builder()
						.id(u.orElseThrow().getUserFrom())
						.statusCode(u.get().getStatus())
						.friendID(u.get().getUserTo())
						.previousStatusCode(u.get().getPreviousStatusCode())
						.rating(123)
						.build()).collect(Collectors.toList());
	}

	@Override
	public FriendShortDto getById(Long id) {
		checkUserIdAndMainId(id);
		Status usersStatus = checkStatusIfExistOrCreate(mainId, id, NONE);

		return createFriendShortDto(
				id,
				false,
				usersStatus.getPreviousStatusCode(),
				usersStatus.getStatus(),
				0);
	}

	@Override
	public void deleteById(Long id) {
		checkUserIdAndMainId(id);
		checkMainIdNotEqualsId(id);
		Status usersStatusTo = getStatusOrElseThrow(mainId, id);
		Status usersStatusFrom = getStatusOrElseThrow(id, mainId);

		changeStatus(usersStatusTo, NONE);

		if (usersStatusFrom.getStatus().equals(FRIEND)) {
			changeStatus(usersStatusFrom, SUBSCRIBED);
		}
	}

	@Override
	public List<Long> getStatusFriend(String status) {
		if (status.isEmpty()) {
			throw new ResourceNotFoundException(ERROR_MESSAGE_STATUS_NOT_FOUND + status);
		}
		List<Long> userList = new ArrayList<>();
		stream(StatusCode.values())
				.filter(statusUser -> statusUser.toString().equals(status.toUpperCase()))
				.forEach(statusUser -> userList.addAll(statusRepository.getUserListByStatus(statusUser.toString())));
		return userList;
	}

	@Override
	public List<FriendShortDto> getRecommendations() {
		List<Users> recommendationList = userRepository.findAllByOrderByRatingDesc();
		List<FriendShortDto> friendShortDtoList = new ArrayList<>();

		for (Users users : recommendationList) {
			Optional<Status> status = statusRepository.findByUserToAndUserFrom(mainId, users.getUserId());
			if ((status.isEmpty()
					|| status.get().getStatus().equals(NONE)
					|| status.get().getStatus().equals(REQUEST_FROM))
					&& !users.getUserId().equals(mainId)) {

				FriendShortDto friendShortDto = FriendShortDto.builder()
						.previousStatusCode(status.isEmpty() ? NONE : status.get().getPreviousStatusCode())
						.statusCode(status.isEmpty() ? NONE : status.get().getStatus())
						.friendID(mainId).rating(users.getRating())
						.id(users.getId())
						.build();
				friendShortDtoList.add(friendShortDto);
			}
		}
		return friendShortDtoList.stream().limit(10).collect(Collectors.toList());
	}

	@Override
	public List<Long> getFriendId() {
		return statusRepository.getUserListByStatusFriend(mainId, FRIEND.toString());
	}

	@Override
	public List<Long> getFriendById(Long id) {
		checkUserIdAndMainId(id);
		return statusRepository.getUserListByStatusFriend(id, FRIEND.toString());
	}

	@Override
	public CountDto getCount() {
		return CountDto
				.builder()
				.count(statusRepository.countByUserToAndStatus(mainId, REQUEST_FROM))
				.build();
	}

	@Override
	public List<String> checkFriend(ArrayList<String> ids) {
		List<String> statusList = new ArrayList<>();
		ids.forEach(s -> statusRepository.findByUserToAndUserFrom(mainId, Long.parseLong(s))
				.ifPresent(status -> statusList.add(status.getStatus().toString())));
		return statusList;
	}

	@Override
	public List<Long> getBlockFriendId() {
		return statusRepository.getUserListByStatusFriend(mainId, BLOCKED.toString());
	}

	public void setMainUserId(Long userid) {
		mainId = userid;
	}

	private void checkUserIdAndMainId(Long id) {
		Optional<Users> user = userRepository.findByUserId(id);
		Optional<Users> mainUser = userRepository.findByUserId(mainId);

		if (user.isEmpty() || mainUser.isEmpty()) {
			throw new ResourceNotFoundException(ERROR_MESSAGE_USER_NOT_FOUND + (mainUser.isEmpty() ? mainId : id));
		} else {
			user.get().setRating(user.get().getRating() + 1);
		}
		if (mainId == null) {
			throw new ResourceNotFoundException(ERROR_MESSAGE_ID_NOT_FOUND);
		}
	}

	public FriendShortDto createFriendShortDto(Long friendId,
	                                           boolean deleted,
	                                           StatusCode previousStatusCode,
	                                           StatusCode statusCode,
	                                           Integer rating) {
		try {
			notificatorProcessor.friendRequestNotificator(mainId, friendId);
		} catch (RuntimeException e) {
			log.error(" ! Unable to send notification");
		}

		return FriendShortDto.builder()
				.id(mainId)
				.rating(rating)
				.isDeleted(deleted)
				.friendID(friendId)
				.statusCode(statusCode)
				.previousStatusCode(previousStatusCode)
				.build();
	}

	public Status createNewStatus(Long userIdTo,
	                              Long userIdFrom,
	                              StatusCode statusCode,
	                              StatusCode previousStatusCode) {
		return Status.builder()
				.userTo(userIdTo)
				.userFrom(userIdFrom)
				.status(statusCode)
				.previousStatusCode(previousStatusCode)
				.build();
	}

	private Status getStatusOrElseThrow(Long userTo, Long userFrom) {
		Optional<Status> status = statusRepository.findByUserToAndUserFrom(userTo, userFrom);
		return status.orElseThrow(() -> new ResourceNotFoundException(ERROR_MESSAGE_STATUS_NOT_FOUND));
	}

	private boolean isExist(Long userTo, Long userFrom) {
		return statusRepository.existsByUserToAndUserFrom(userTo, userFrom);
	}

	private void saveStatus(Status status) {
		statusRepository.save(status);
	}

	private Status checkStatusIfExistOrCreate(Long userIdTo, Long userIdFrom, StatusCode statusCode) {
		return isExist(userIdTo, userIdFrom) ? getStatusOrElseThrow(userIdTo, userIdFrom)
				: createNewStatus(userIdTo, userIdFrom, statusCode, NONE);
	}

	private void checkMainIdNotEqualsId(Long id) {
		if (id.equals(mainId)) {
			throw new ResourceNotFoundException(ERROR_MESSAGE_CHECK_MAIN_ID);
		}
	}

	private void changeStatus(Status status, StatusCode statusCode) {
		status.setPreviousStatusCode(status.getStatus());
		status.setStatus(statusCode);
		saveStatus(status);
	}
}
