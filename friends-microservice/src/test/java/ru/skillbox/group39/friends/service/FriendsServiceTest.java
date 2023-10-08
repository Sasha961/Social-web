package ru.skillbox.group39.friends.service;

import org.springframework.boot.test.mock.mockito.MockBean;
import ru.skillbox.group39.friends.database.BuildingPostgresqlContainer;
import ru.skillbox.group39.friends.dto.friend.FriendShortDto;
import ru.skillbox.group39.friends.kafka.KafkaConsumer;
import ru.skillbox.group39.friends.kafka.KafkaProducer;
import ru.skillbox.group39.friends.service.impl.FriendsServiceImpl;
import ru.skillbox.group39.friends.dto.enums.StatusCode;
import ru.skillbox.group39.friends.dto.CountDto;
import ru.skillbox.group39.friends.model.Status;
import ru.skillbox.group39.friends.model.Users;
import ru.skillbox.group39.friends.repository.StatusRepository;
import ru.skillbox.group39.friends.repository.UserRepository;
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

import java.util.List;
import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FriendsServiceTest {

    @ClassRule
    public static PostgreSQLContainer<BuildingPostgresqlContainer> postgreSQLContainer = BuildingPostgresqlContainer.getInstance();

    @MockBean
    private KafkaConsumer kafkaConsumer;
    @MockBean
    private KafkaProducer kafkaProducer;
    @Autowired
    private FriendsServiceImpl friendsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;


    @Before
    public void setUp() {


        Status status = Status.builder()
                .status(StatusCode.NONE)
                .userTo(1L)
                .userFrom(2L)
                .deleted(false)
                .previousStatusCode(StatusCode.NONE)
                .build();

        Status status1 = Status.builder()
                .status(StatusCode.REQUEST_TO)
                .userTo(2L)
                .userFrom(1L)
                .deleted(false)
                .previousStatusCode(StatusCode.NONE)
                .build();

        Status status2 = Status.builder()
                .status(StatusCode.BLOCKED)
                .userTo(3L)
                .userFrom(2L)
                .deleted(false)
                .previousStatusCode(StatusCode.NONE)
                .build();

        friendsService.setMainUserId(Long.parseLong("1"));

        statusRepository.save(status);
        statusRepository.save(status1);
        statusRepository.save(status2);
        statusRepository.flush();

        Users user = Users.builder()
                .userId(1L)
                .rating(123)
                .identification_id("23ewds3ewds")
                .build();

        Users user1 = Users.builder()
                .userId(2L)
                .rating(42133)
                .identification_id("21344r142")
                .build();

        Users user2 = Users.builder()
                .userId(3L)
                .rating(21321)
                .identification_id("21344r142")
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
        FriendShortDto friendShortDto = friendsService.approvedFriend(2L);

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusCode.FRIEND);
    }

    @Test
    public void unBlockTest_returnFriendShortDtoNotNullWithStatusUserUnBlock() {

        friendsService.setMainUserId(Long.parseLong("3"));
        FriendShortDto friendShortDto = friendsService.unBlock(2L);

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusCode.NONE);
        Assertions.assertEquals(friendShortDto.getPreviousStatusCode(), StatusCode.BLOCKED);
    }

    @Test
    public void blockUserTest_returnFriendShortDtoNotNullWithStatusUserBlock() {

        FriendShortDto friendShortDto = friendsService.block(2L);

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusCode.BLOCKED);
    }

    @Test
    public void requestFriendTest_returnFriendShortDtoNotNullWith() {

        FriendShortDto friendShortDto = friendsService.approvedFriend(2L);

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusCode.FRIEND);
    }

    @Test
    public void subscribeTest_returnFriendShortDtoNotNullWithStatusUserRequestTo() {

        FriendShortDto friendShortDto = friendsService.subscribe(3L);

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusCode.SUBSCRIBED);
    }

//    @Test
//    public void getAllTest(){
//
//    }

    @Test
    public void getByIdTest_returnFriendShortDtoNotNull() {

        FriendShortDto friendShortDto = friendsService.getById(1L);

        Assertions.assertNotNull(friendShortDto);
        Assertions.assertEquals(friendShortDto.getStatusCode(), StatusCode.NONE);
    }

    @Test
    public void deleteByIdTest_returnStatusNotNullWithStatusNone() {

        friendsService.setMainUserId(2L);
        friendsService.deleteById(1L);
        Optional<Status> status = statusRepository.findByUserToAndUserFrom(2L, 1L);

        Assertions.assertNotNull(status);
        Assertions.assertEquals(status.get().getStatus(), StatusCode.NONE);
    }

    @Test
    public void statusFriendTest_returnListUserIdNotNull() {

        List<Long> listUsers = friendsService.getStatusFriend("BLOCKED");

        Assertions.assertNotNull(listUsers);
        Assertions.assertEquals(listUsers.size(), 1);
    }

//    @Test
//    public void getRecommendationTest(){
//
//    }

    @Test
    public void getFriendIdTest_returnUserListNotNull() {

        friendsService.approvedFriend(2L);
        List<Long> userList = friendsService.getFriendId();

        Assertions.assertNotNull(userList);
        Assertions.assertEquals(userList.size(), 1);
    }

    @Test
    public void getFriendByIdTest_return_ListFriendIdNotNull() {
        friendsService.approvedFriend(2L);
        List<Long> frindlist = friendsService.getFriendById(1L);

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

        List<Long> userList = friendsService.getBlockFriendId();

        Assertions.assertNotNull(userList);
        Assertions.assertEquals(userList.size(), 0);
    }

}
