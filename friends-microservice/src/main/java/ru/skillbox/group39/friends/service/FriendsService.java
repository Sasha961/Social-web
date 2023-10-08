package ru.skillbox.group39.friends.service;

import ru.skillbox.group39.friends.dto.friend.FriendShortDto;
import ru.skillbox.group39.friends.dto.CountDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface FriendsService {

    FriendShortDto approvedFriend(Long id);

    FriendShortDto unBlock(Long id);

    FriendShortDto block(Long id);

    FriendShortDto requestFriend(Long id);

    FriendShortDto subscribe(Long id);

    List<FriendShortDto> getAll(Integer size, String code, String statusCode, Integer ageFrom, Integer ageTo);

    FriendShortDto getById(Long id);

    void deleteById(Long id);

    List<Long> getStatusFriend(String status);

    List<FriendShortDto> getRecommendations();

    List<Long> getFriendId();

    List<Long> getFriendById(Long id);

    CountDto getCount();

    List<String> checkFriend(ArrayList<String> ids);

    List<Long> getBlockFriendId();

}
