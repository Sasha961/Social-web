package com.friends.demo.service;

import com.friends.demo.database.BuildingPostgresqlContainer;
import com.friends.demo.dto.CountDto;
import com.friends.demo.dto.Enum.StatusUser;
import com.friends.demo.dto.friend.FriendShortDto;
import com.friends.demo.model.Friends;
import com.friends.demo.model.Status;
import com.friends.demo.model.Users;
import com.friends.demo.repository.FriendRepository;
import com.friends.demo.repository.HistoryRepository;
import com.friends.demo.repository.StatusRepository;
import com.friends.demo.repository.UserRepository;
import com.friends.demo.service.impl.FriendsServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FriendsServiceTest {

    @ClassRule
    public static PostgreSQLContainer<BuildingPostgresqlContainer> postgreSQLContainer = BuildingPostgresqlContainer.getInstance();

    @Autowired
    FriendsServiceImpl friendsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private StatusRepository statusRepository;


    @Before
    public void setUp() {

        Status status = Status.builder()
                .status(StatusUser.NONE)
                .userId1("1")
                .userId2("2")
                .deleted(false)
                .previousStatusCode(StatusUser.NONE)
                .build();

        Status status1 = Status.builder()
                .status(StatusUser.REQUEST_TO)
                .userId1("2")
                .userId2("1")
                .deleted(false)
                .previousStatusCode(StatusUser.NONE)
                .build();

        Status status2 = Status.builder()
                .status(StatusUser.BLOCKED)
                .userId1("3")
                .userId2("2")
                .deleted(false)
                .previousStatusCode(StatusUser.NONE)
                .build();

        friendsService.setMainUserId("1");

        statusRepository.save(status);
        statusRepository.save(status1);
        statusRepository.save(status2);
        statusRepository.flush();

        Users user = Users.builder()
                .userId("1")
                .rating(123)
                .userHistory(new ArrayList<>())
                .identification_id("23ewds3ewds")
                .friends(new Friends())
                .build();

        Users user1 = Users.builder()
                .userId("2")
                .rating(42133)
                .userHistory(new ArrayList<>())
                .identification_id("21344r142")
                .friends(new Friends())
                .build();

        Users user2 = Users.builder()
                .userId("3")
                .rating(21321)
                .userHistory(new ArrayList<>())
                .identification_id("21344r142")
                .friends(new Friends())
                .build();

        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.flush();

    }

    @After
    public void clearDataBase() {
        userRepository.deleteAll();
        statusRepository.deleteAll();
    }

    @Test
    public void approvedFriendTest_returnFriendShortDtoNotNullWithStatusUserFriend() {

        List<Users> usersList = userRepository.findAll();
        FriendShortDto friendShortDto = friendsService.approvedFriend("2");

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusUser.FRIEND);
    }

    @Test
    public void unBlockTest_returnFriendShortDtoNotNullWithStatusUserUnBlock() {

        friendsService.setMainUserId("3");
        FriendShortDto friendShortDto = friendsService.unBlock("2");

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusUser.NONE);
        Assertions.assertEquals(friendShortDto.getPreviousStatusCode(), StatusUser.BLOCKED);
    }

    @Test
    public void blockUserTest_returnFriendShortDtoNotNullWithStatusUserBlock() {

        FriendShortDto friendShortDto = friendsService.block("2");

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusUser.BLOCKED);
    }

    @Test
    public void requestFriendTest_returnFriendShortDtoNotNullWith() {

        FriendShortDto friendShortDto = friendsService.approvedFriend("2");

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusUser.FRIEND);
    }

    @Test
    public void subscribeTest_returnFriendShortDtoNotNullWithStatusUserRequestTo() {

        FriendShortDto friendShortDto = friendsService.subscribe("3");

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusUser.REQUEST_TO);
    }

//    @Test
//    public void getAllTest(){
//
//    }

    @Test
    public void getByIdTest_returnFriendShortDtoNotNull() {

        FriendShortDto friendShortDto = friendsService.getById("1");

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusUser.NONE);
    }

    @Test
    public void deleteByIdTest_returnStatusNotNullWithStatusNone() {

        friendsService.setMainUserId("2");
        friendsService.deleteById("1");
        Optional<Status> status = statusRepository.findByUserId1AndUserId2("2", "1");

        Assertions.assertNotNull(status);
        Assertions.assertEquals(status.get().getStatus(), StatusUser.NONE);
    }

    @Test
    public void statusFriendTest_returnListUserIdNotNull() {

        List<String> listUsers = friendsService.statusFriend("BLOCKED");

        Assertions.assertNotNull(listUsers);
        Assertions.assertEquals(listUsers.size(), 1);
    }

//    @Test
//    public void getRecommendationTest(){
//
//    }

    @Test
    public void getFriendIdTest_returnUserListNotNull() {

        friendsService.approvedFriend("2");
        List<String> userList = friendsService.getFriendId();

        Assertions.assertNotNull(userList);
        Assertions.assertEquals(userList.size(), 1);
    }

    @Test
    public void getFriendByIdTest_return_ListFriendIdNotNull() {
        friendsService.approvedFriend("2");
        List<String> frindlist = friendsService.getFriendById("1");

        Assertions.assertNotNull(frindlist);
        Assertions.assertEquals(frindlist.size(), 1);
    }

    @Test
    public void getCountTest_returnCountDtoNotNull() {

        CountDto countDto = friendsService.getCount();

        Assertions.assertNotNull(countDto);
        Assertions.assertEquals(countDto.count, 0);
    }

//    @Test
//    public void checkFriendTest_returnStatusListNotNull() {
//
//        ArrayList<String> statusList = (ArrayList<String>) List.of("NONE", "BLOCKED");
//        List<String> result = friendsService.checkFriend(statusList);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(result.size(), 2);
//
//    }

    @Test
    public void getBlockFriendIdTest_returnListUserIdNotNull() {

        List<String> userList = friendsService.getBlockFriendId();

        Assertions.assertNotNull(userList);
        Assertions.assertEquals(userList.size(), 0);
    }

}
