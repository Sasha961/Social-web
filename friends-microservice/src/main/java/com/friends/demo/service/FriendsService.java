package com.friends.demo.service;
import com.friends.demo.dto.CountDto;
import com.friends.demo.dto.friend.FriendSearchDto;
import com.friends.demo.dto.friend.FriendShortDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface FriendsService {

    FriendShortDto approvedFriend(String id);

    FriendShortDto unBlock(String id);

    FriendShortDto block(String id);
    FriendShortDto requestFriend(String id);

    FriendShortDto subscribe(String id);

    FriendShortDto getAll(FriendSearchDto searchDto, Integer page, Integer size, ArrayList<String> sort);

    FriendShortDto getById(String id);

    void deleteById(String id);

    List<String> statusFriend(String status);

    List<FriendShortDto> getRecommendations(FriendSearchDto searchDto);

    List<String> getFriendId();

    List<String> getFriendById(String id);

    CountDto getCount();

    List<String> checkFriend(ArrayList<String> ids);

    List<String> getBlockFriendId();
}
