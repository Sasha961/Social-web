package ru.skillbox.group39.friends.controller;

import ru.skillbox.group39.friends.dto.friend.FriendShortDto;
import ru.skillbox.group39.friends.service.impl.FriendsServiceImpl;
import ru.skillbox.group39.friends.dto.CountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendsControllerImpl implements FriendsController {

    private final FriendsServiceImpl friendsService;

    @Override
    public FriendShortDto approvedFriend(Long id) {
        return friendsService.approvedFriend(id);
    }

    @Override
    public FriendShortDto unBlock(Long id) {
        return friendsService.unBlock(id);
    }

    @Override
    public FriendShortDto block(Long id) {
        return friendsService.block(id);
    }

    @Override
    public FriendShortDto requestFriend(Long id) {
        return friendsService.requestFriend(id);
    }

    @Override
    public FriendShortDto subscribe(Long id) {
        return friendsService.subscribe(id);
    }

    @Override
    public List<FriendShortDto> getAll(Integer size, String statusCode, String firstName, Integer ageFrom, Integer ageTo) {
        return friendsService.getAll(size, statusCode, firstName, ageFrom, ageTo);
    }

    @Override
    public FriendShortDto getById(Long id) {
        return friendsService.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        friendsService.deleteById(id);
    }

    @Override
    public List<Long> getStatusFriend(String status) {
        return friendsService.getStatusFriend(status);
    }

    @Override
    public List<FriendShortDto> getRecommendations() {
        return friendsService.getRecommendations();
    }

    @Override
    public List<Long> getFriendId() {
        return friendsService.getFriendId();
    }

    @Override
    public List<Long> getFriendById(Long id) {
        return friendsService.getFriendById(id);
    }

    @Override
    public CountDto getCount() {
        return friendsService.getCount();
    }

    @Override
    public List<String> checkFriend(ArrayList<String> ids) {
        return friendsService.checkFriend(ids);
    }

    @Override
    public List<Long> getBlockFriendId() {
        return friendsService.getBlockFriendId();
    }
}
