package com.friends.demo.repository;

import com.friends.demo.database.BuildingPostgresqlContainer;
import com.friends.demo.dto.Enum.StatusUser;
import com.friends.demo.model.Status;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Testcontainers
public class StatusRepositoryTest {

    @ClassRule
    @Container
    public static PostgreSQLContainer<BuildingPostgresqlContainer> postgreSQLContainer = BuildingPostgresqlContainer.getInstance();

    @Autowired
    StatusRepository statusRepository;

    @Before
    @Transactional
    public void setUp() {

        Status status = Status.builder()
                .id(1L)
                .userId1("1")
                .userId2("2")
                .previousStatusCode(StatusUser.NONE)
                .status(StatusUser.FRIEND)
                .deleted(false)
                .build();

        Status status2 = Status.builder()
                .id(2L)
                .userId1("5")
                .userId2("6")
                .previousStatusCode(StatusUser.NONE)
                .status(StatusUser.FRIEND)
                .deleted(false)
                .build();

        Status status3 = Status.builder()
                .id(3L)
                .userId1("2")
                .userId2("1")
                .previousStatusCode(StatusUser.NONE)
                .status(StatusUser.FRIEND)
                .deleted(false)
                .build();
        statusRepository.save(status);
        statusRepository.save(status2);
        statusRepository.save(status3);
        statusRepository.flush();
    }

    @Test
    @Transactional
    public void saveAll_returnSaveAllStatus() {

        Status status = Status.builder()
                .userId1("3")
                .userId2("4")
                .previousStatusCode(StatusUser.NONE)
                .status(StatusUser.REQUEST_TO)
                .deleted(false)
                .build();
        Status saveStatus = statusRepository.save(status);

        Assertions.assertNotNull(saveStatus);
        org.assertj.core.api.Assertions.assertThat(saveStatus.getId()).isGreaterThan(0);
    }

    @Test
    @Transactional
    public void findById_returnStatusNotNull() {

        Optional<Status> status = statusRepository.findById(21L);

        Assertions.assertTrue(status.isPresent());
        org.assertj.core.api.Assertions.assertThat(status.get()).isNotNull();
    }

    @Test
    @Transactional
    public void findAll_returnStatusListNotNull() {

        List<Status> list = statusRepository.findAll();

        Assertions.assertNotNull(list);
        Assertions.assertEquals(list.size(), 3);
    }

    @Test
    @Transactional
    public void updateUsersStatus_returnUsersStatusNotNull() {

        Status status = statusRepository.findById(8L).get();
        status.setStatus(StatusUser.BLOCKED);
        statusRepository.save(status);

        Assertions.assertNotNull(status.getStatus());
        Assertions.assertEquals(status.getStatus(), StatusUser.BLOCKED);
    }

    @Test
    @Transactional
    public void findStatusByUserId1AndUserId2_returnStatusNotNull() {

        Optional<Status> statusUser = statusRepository.findByUserId1AndUserId2("1", "2");

        Assertions.assertNotNull(statusUser.get());
    }

    @Test
    @Transactional
    public void gegListUserIdByUserStatus_returnStatusListNotNull() {

        List<String> statusList = statusRepository.getUserListByStatus(StatusUser.FRIEND.ordinal());

        Assertions.assertNotNull(statusList);
        Assertions.assertEquals(statusList.size(), 3);
    }

    @Test
    @Transactional
    public void getListUserIdByStatusFriend_returnUserList() {

        List<String> userList = statusRepository.getUserListByStatusFriend("1", StatusUser.FRIEND.ordinal());

        Assertions.assertNotNull(userList);
        Assertions.assertEquals(userList.size(), 1);
    }

    @Test
    @Transactional
    public void countByUserId1AndStatus_returnCountNotNull() {

        Integer count = statusRepository.countByUserId1AndStatus("1", StatusUser.FRIEND);

        Assertions.assertEquals(count, 1);
    }

    @Test
    @Transactional
    public void deleteById_returnStatusIsEmpty() {

        statusRepository.deleteById(14L);
        Optional<Status> status = statusRepository.findById(14L);

        org.assertj.core.api.Assertions.assertThat(status).isEmpty();
    }
}
