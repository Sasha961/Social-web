package com.friends.demo.controller;

import com.friends.demo.controller.FriendsController;
import com.friends.demo.dto.CountDto;
import com.friends.demo.dto.friend.FriendSearchDto;
import com.friends.demo.dto.friend.FriendShortDto;
import com.friends.demo.service.impl.FriendsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendsControllerImpl implements FriendsController {

    private final FriendsServiceImpl friendsService;

    @Override
    public FriendShortDto approvedFriend(String id) {
        return friendsService.approvedFriend(id);
    }

    @Override
    public FriendShortDto unBlock(String id) {
        return friendsService.unBlock(id);
    }

    @Override
    public FriendShortDto block(String id) {
        return friendsService.block(id);
    }

    @Override
    public FriendShortDto requestFriend(String id) {
        return friendsService.requestFriend(id);
    }

    @Override
    public FriendShortDto subscribe(String id) {
        return friendsService.subscribe(id);
    }

    @Override
    public FriendShortDto getAll(FriendSearchDto searchDto, Integer page, Integer size, ArrayList<String> sort) {
        return friendsService.getAll(searchDto, page, size, sort);
    }

    @Override
    public FriendShortDto getById(String id) {
        return friendsService.getById(id);
    }

    @Override
    public void deleteById(String id) {
        friendsService.deleteById(id);
    }

    @Override
    public List<String> statusFriend(String status) {
        return friendsService.statusFriend(status);
    }

    @Override
    public List<FriendShortDto> getRecommendations(FriendSearchDto friendShortDto) {
        return null;
    }

    @Override
    public List<String> getFriendId() {
        return friendsService.getFriendId();
    }

    @Override
    public List<String> getFriendById(String id) {
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
    public List<String> getBlockFriendId() {
        return friendsService.getBlockFriendId();
    }
}
