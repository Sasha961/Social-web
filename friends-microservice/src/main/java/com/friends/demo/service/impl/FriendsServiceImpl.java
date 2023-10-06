package com.friends.demo.service.impl;

import com.friends.demo.dto.CountDto;
import com.friends.demo.dto.Enum.StatusUser;
import com.friends.demo.dto.friend.FriendSearchDto;
import com.friends.demo.dto.friend.FriendShortDto;
import com.friends.demo.globalHendler.ResourceNotFoundException;
import com.friends.demo.model.Status;
import com.friends.demo.model.Users;
import com.friends.demo.repository.FriendRepository;
import com.friends.demo.repository.HistoryRepository;
import com.friends.demo.repository.StatusRepository;
import com.friends.demo.repository.UserRepository;
import com.friends.demo.service.FriendsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.friends.demo.dto.Enum.StatusUser.*;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {

//    private final UsersController usersController;

    private final UserRepository userRepository;

    private final FriendRepository friendRepository;

    private final HistoryRepository historyRepository;

    private final StatusRepository statusRepository;

    private static String mainId;

    @Override
    public FriendShortDto approvedFriend(String id) {

        checkUser(id);

        Optional<Status> usersStatus1 = statusRepository.findByUserId1AndUserId2(mainId, id);
        Optional<Status> usersStatus2 = statusRepository.findByUserId1AndUserId2(id, mainId);

        FriendShortDto friendShortDto = new FriendShortDto();
        friendShortDto.setFriendID(id);
        friendShortDto.setRating(0);
        friendShortDto.setDeleted(true);
        friendShortDto.setId(mainId);

        if (usersStatus1.isPresent() && usersStatus2.isPresent()
                && !usersStatus1.get().getStatus().equals(StatusUser.BLOCKED)) {
            friendShortDto.setPreviousStatusCode(usersStatus1.get().getStatus());
            usersStatus1.get().setPreviousStatusCode(usersStatus1.get().getStatus());
            usersStatus2.get().setPreviousStatusCode(usersStatus2.get().getStatus());
            usersStatus1.get().setStatus(StatusUser.FRIEND);
            usersStatus2.get().setStatus(StatusUser.FRIEND);
            statusRepository.save(usersStatus1.get());
            statusRepository.save(usersStatus2.get());
            friendShortDto.setStatusCode(StatusUser.FRIEND);
        } else if (usersStatus1.isPresent() && usersStatus1.get().getStatus().equals(StatusUser.BLOCKED)) {
            friendShortDto.setPreviousStatusCode(usersStatus1.get().getPreviousStatusCode());
            friendShortDto.setStatusCode(StatusUser.BLOCKED);
        }
        return friendShortDto;
    }

    @Override
    public FriendShortDto unBlock(String id) {

        checkUser(id);

        Optional<Status> usersStatus = statusRepository.findByUserId1AndUserId2(mainId, id);
        FriendShortDto friendShortDto = new FriendShortDto();
        friendShortDto.setId(mainId);
        friendShortDto.setRating(0);
        friendShortDto.setDeleted(true);
        friendShortDto.setFriendID(id);

        if (usersStatus.isPresent() && usersStatus.get().getStatus().equals(StatusUser.BLOCKED)) {
            friendShortDto.setPreviousStatusCode(usersStatus.get().getStatus());
            usersStatus.get().setPreviousStatusCode(usersStatus.get().getStatus());
            usersStatus.get().setStatus(NONE);
            friendShortDto.setStatusCode(usersStatus.get().getStatus());
            statusRepository.save(usersStatus.get());
        }
        return friendShortDto;
    }

    @Override
    public FriendShortDto block(String id) {

        checkUser(id);

        Optional<Status> usersStatus1 = statusRepository.findByUserId1AndUserId2(mainId, id);
        Optional<Status> usersStatus2 = statusRepository.findByUserId1AndUserId2(id, mainId);

        FriendShortDto friendShortDto = new FriendShortDto();
        friendShortDto.setId(id);
        friendShortDto.setRating(0);
        friendShortDto.setDeleted(true);
        friendShortDto.setId(mainId);
        friendShortDto.setFriendID(id);

        if (usersStatus2.isPresent() && usersStatus2.get().getStatus().equals(StatusUser.FRIEND)) {
            usersStatus2.get().setPreviousStatusCode(usersStatus2.get().getStatus());
            usersStatus2.get().setStatus(StatusUser.REQUEST_TO);
            friendShortDto.setPreviousStatusCode(usersStatus2.get().getPreviousStatusCode());
            statusRepository.save(usersStatus2.get());
        }

        if (usersStatus1.isPresent() && !usersStatus1.get().getStatus().equals(StatusUser.BLOCKED)) {
            friendShortDto.setPreviousStatusCode(usersStatus1.get().getStatus());
            usersStatus1.get().setPreviousStatusCode(usersStatus1.get().getStatus());
            usersStatus1.get().setStatus(StatusUser.BLOCKED);
            friendShortDto.setStatusCode(usersStatus1.get().getStatus());
            statusRepository.save(usersStatus1.get());
        }
        return friendShortDto;
    }

    @Override
    public FriendShortDto requestFriend(String id) {

        checkUser(id);

        Optional<Status> usersStatus1 = statusRepository.findByUserId1AndUserId2(mainId, id);
        Optional<Status> usersStatus2 = statusRepository.findByUserId1AndUserId2(id, mainId);

        FriendShortDto friendShortDto = new FriendShortDto();
        friendShortDto.setId(mainId);
        friendShortDto.setFriendID(id);
        friendShortDto.setRating(0);
        friendShortDto.setDeleted(true);

        if (usersStatus1.isEmpty()) {
            statusRepository.save(Status.builder()
                    .userId1(mainId)
                    .userId2(id)
                    .status(StatusUser.REQUEST_TO)
                    .previousStatusCode(NONE)
                    .build());
        } else if (!usersStatus1.get().getStatus().equals(StatusUser.BLOCKED)) {
            usersStatus1.get().setPreviousStatusCode(usersStatus1.get().getStatus());
            usersStatus1.get().setStatus(StatusUser.REQUEST_TO);
            friendShortDto.setPreviousStatusCode(usersStatus1.get().getPreviousStatusCode());
            friendShortDto.setStatusCode(usersStatus1.get().getStatus());
            statusRepository.save(usersStatus1.get());
        }

        if (usersStatus2.isEmpty()) {
            statusRepository.save(Status.builder()
                    .userId1(id)
                    .userId2(mainId)
                    .status(NONE)
                    .build());
        }

        if ((usersStatus1.isPresent() && usersStatus1.get().getStatus().equals(StatusUser.REQUEST_TO))
                && (usersStatus2.isPresent() && usersStatus2.get().getStatus().equals(StatusUser.REQUEST_TO))) {
            usersStatus1.get().setPreviousStatusCode(usersStatus1.get().getStatus());
            usersStatus2.get().setPreviousStatusCode(usersStatus2.get().getStatus());
            usersStatus1.get().setStatus(StatusUser.FRIEND);
            usersStatus2.get().setStatus(StatusUser.FRIEND);
            statusRepository.save(usersStatus1.get());
            statusRepository.save(usersStatus2.get());
            friendShortDto.setPreviousStatusCode(usersStatus1.get().getPreviousStatusCode());
            friendShortDto.setStatusCode(usersStatus1.get().getStatus());
        }

        return friendShortDto;
    }

    @Override
    public FriendShortDto subscribe(String id) {

        checkUser(id);

        Optional<Status> usersStatus1 = statusRepository.findByUserId1AndUserId2(mainId, id);

        FriendShortDto friendShortDto = new FriendShortDto();
        friendShortDto.setId(mainId);
        friendShortDto.setFriendID(id);
        friendShortDto.setRating(0);
        friendShortDto.setDeleted(true);

        if (usersStatus1.isEmpty()) {
            statusRepository.save(Status.builder()
                    .userId1(mainId)
                    .userId2(id)
                    .status(StatusUser.REQUEST_TO)
                    .previousStatusCode(NONE)
                    .build());
            friendShortDto.setPreviousStatusCode(NONE);
            friendShortDto.setStatusCode(StatusUser.REQUEST_TO);
        } else {
            usersStatus1.get().setPreviousStatusCode(usersStatus1.get().getStatus());
            usersStatus1.get().setStatus(StatusUser.REQUEST_TO);
            friendShortDto.setPreviousStatusCode(usersStatus1.get().getPreviousStatusCode());
            friendShortDto.setStatusCode(usersStatus1.get().getStatus());
            statusRepository.save(usersStatus1.get());
        }
        return friendShortDto;
    }

    @Override
    public FriendShortDto getAll(FriendSearchDto searchDto, Integer page, Integer size, ArrayList<String> sort) {

        // что за параметр sort, size

        return FriendShortDto.builder().build();
    }

    @Override
    public FriendShortDto getById(String id) {

        checkUser(id);

        Optional<Status> usersStatus = statusRepository.findByUserId1AndUserId2(mainId, id);
        FriendShortDto friendShortDto = new FriendShortDto();
        friendShortDto.setId(mainId);
        friendShortDto.setFriendID(id);
        friendShortDto.setRating(0);
        friendShortDto.setDeleted(true);

        if (usersStatus.isPresent()) {
            friendShortDto.setStatusCode(usersStatus.get().getStatus());
            friendShortDto.setPreviousStatusCode(usersStatus.get().getPreviousStatusCode());
        } else {
            friendShortDto.setStatusCode(NONE);
            friendShortDto.setPreviousStatusCode(NONE);
        }
        return friendShortDto;
    }

    @Override
    public void deleteById(String id) {

        checkUser(id);

        Optional<Status> usersStatus1 = statusRepository.findByUserId1AndUserId2(mainId, id);
        Optional<Status> usersStatus2 = statusRepository.findByUserId1AndUserId2(id, mainId);

        if (usersStatus1.isPresent()) {
            usersStatus1.get().setPreviousStatusCode(usersStatus1.get().getStatus());
            usersStatus1.get().setStatus(NONE);
            statusRepository.save(usersStatus1.get());
        }
        if (usersStatus2.isPresent() && usersStatus2.get().getStatus().equals(StatusUser.FRIEND)) {
            usersStatus2.get().setPreviousStatusCode(usersStatus2.get().getStatus());
            usersStatus2.get().setStatus(StatusUser.REQUEST_TO);
            statusRepository.save(usersStatus2.get());
        }
    }

    @Override
    public List<String> statusFriend(String status) {

        if (status.isEmpty())
            throw new ResourceNotFoundException("status: " + status + " not found");

        List<String> userList = new ArrayList<>();
        Arrays.stream(StatusUser.values())
                .filter(statusUser -> statusUser.toString().equals(status.toUpperCase()))
                .forEach(statusUser -> userList.addAll(statusRepository.getUserListByStatus(statusUser.ordinal())));
        return userList;
    }

    @Override
    public List<FriendShortDto> getRecommendations(FriendSearchDto searchDto) {
        return new ArrayList<FriendShortDto>();
    }

    @Override
    public List<String> getFriendId() {
        return statusRepository.getUserListByStatusFriend(mainId, FRIEND.ordinal());
    }

    @Override
    public List<String> getFriendById(String id) {
        checkUser(id);
        return statusRepository.getUserListByStatusFriend(id, FRIEND.ordinal());
    }

    @Override
    public CountDto getCount() {
        return CountDto
                .builder()
                .count(statusRepository.countByUserId1AndStatus(mainId, StatusUser.REQUEST_FROM))
                .build();
    }

    @Override
    public List<String> checkFriend(ArrayList<String> ids) {
        List<String> statusList = new ArrayList<>();
        ids.forEach(s -> statusRepository.findByUserId1AndUserId2(mainId, s)
                .ifPresent(status -> statusList.add(status.getStatus().toString())));
        return statusList;
    }

    @Override
    public List<String> getBlockFriendId() {
        return statusRepository.getUserListByStatusFriend(mainId, BLOCKED.ordinal());
    }

    public void setMainUserId(String userid) {
        mainId = userid;
    }

    private void checkUser(String id) {

        if (mainId == null) {
            throw new ResourceNotFoundException("Скорее всего в токене отстутствует параметр(String) с ключом \"userId\" \n пример: claims.put(\"userId\", jwtUser.getUserId());");
        }

        Optional<Users> user = userRepository.findByUserId(id);

        if (user.isEmpty())
            throw new ResourceNotFoundException("user with id: " + id + " not found");
    }
}
