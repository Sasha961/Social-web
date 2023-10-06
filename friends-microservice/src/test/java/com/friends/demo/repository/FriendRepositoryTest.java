package com.friends.demo.repository;

import com.friends.demo.database.BuildingPostgresqlContainer;
import com.friends.demo.model.Friends;
import com.friends.demo.model.Users;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Testcontainers
public class FriendRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;

    @ClassRule
    @Container
    public static PostgreSQLContainer<BuildingPostgresqlContainer> postgreSQLContainer = BuildingPostgresqlContainer.getInstance();

    @Before
    public void setUp() {
        Friends friends = new Friends();
        friends.setUserId("1");
        friends.setFriendsList(new ArrayList<>());
        friendRepository.save(friends);

        Friends friends2 = new Friends();
        friends2.setUserId("2");
        friends2.setFriendsList(new ArrayList<>());

        friendRepository.save(friends);
        friendRepository.save(friends2);
        friendRepository.flush();
    }

    @Test
    public void saveFriend_returnFriendNotNull() {

        Friends friends = new Friends();
        friends.setFriendsList(new ArrayList<>());
        friends.setUserId("3");
        Friends saveFriend = friendRepository.save(friends);

        Assertions.assertNotNull(saveFriend);
        org.assertj.core.api.Assertions.assertThat(saveFriend.getId()).isGreaterThan(0);
    }

    @Test
    public void findById_returnFriendNotNull() {

        Friends friends = new Friends();
        friends.setFriendsList(new ArrayList<>());
        friends.setUserId("3");
        Friends saveFriend = friendRepository.save(friends);
        Friends findFriends = friendRepository.findById(saveFriend.getId()).get();

        Assertions.assertNotNull(findFriends);
    }

    @Test
    public void findAll_returnFriendListNotNull() {

        List<Friends> list = friendRepository.findAll();

        Assertions.assertNotNull(list);
        Assertions.assertEquals(list.size(), 2);
    }

    @Test
    public void updateFriend_returnFriendNotNull() {

        Users users = Users.builder()
                .userId("1")
                .rating(12)
                .userHistory(new ArrayList<>()).id(1L)
                .identification_id("dswgvfrwedfwsd")
                .build();
        Friends friends = friendRepository.findById(3L).get();
        friends.setUserId("15");
        friends.setFriendsList(List.of(users));

        Assertions.assertNotNull(friends.getUserId());
        Assertions.assertNotNull(friends.getFriendsList());
        Assertions.assertEquals(friends.getUserId(), "15");
        Assertions.assertEquals(friends.getFriendsList().size(), 1);
    }

    @Test
    public void deleteById_returnStatusIsEmpty() {

        Friends friend = new Friends();
        friend.setUserId("6");
        friend.setFriendsList(new ArrayList<>());
        Friends saveFriend = friendRepository.save(friend);
        friendRepository.deleteById(saveFriend.getId());
        Optional<Friends> checkFriend = friendRepository.findById(saveFriend.getId());

        org.assertj.core.api.Assertions.assertThat(checkFriend).isEmpty();
    }
}
