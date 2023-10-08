package ru.skillbox.group39.friends.repository;

import ru.skillbox.group39.friends.database.BuildingPostgresqlContainer;
import ru.skillbox.group39.friends.dto.enums.StatusCode;
import ru.skillbox.group39.friends.model.Status;
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
                .userTo(1L)
                .userFrom(2L)
                .previousStatusCode(StatusCode.NONE)
                .status(StatusCode.FRIEND)
                .deleted(false)
                .build();

        Status status2 = Status.builder()
                .id(2L)
                .userTo(5L)
                .userFrom(6L)
                .previousStatusCode(StatusCode.NONE)
                .status(StatusCode.FRIEND)
                .deleted(false)
                .build();

        Status status3 = Status.builder()
                .id(3L)
                .userTo(2L)
                .userFrom(1L)
                .previousStatusCode(StatusCode.NONE)
                .status(StatusCode.FRIEND)
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
                .userTo(3L)
                .userFrom(4L)
                .previousStatusCode(StatusCode.NONE)
                .status(StatusCode.REQUEST_TO)
                .deleted(false)
                .build();
        Status saveStatus = statusRepository.save(status);

        Assertions.assertNotNull(saveStatus);
        org.assertj.core.api.Assertions.assertThat(saveStatus.getId()).isGreaterThan(0);
    }

    @Test
    @Transactional
    public void findById_returnStatusNotNull() {
        Status status1 = new Status();
        status1.setStatus(StatusCode.NONE);
        statusRepository.save(status1);

        Optional<Status> status = statusRepository.findById(status1.getId());

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

        Status status1 = new Status();
        status1.setStatus(StatusCode.NONE);
        statusRepository.save(status1);

        Status status = statusRepository.findById(status1.getId()).get();
        status.setStatus(StatusCode.BLOCKED);
        statusRepository.save(status);

        Assertions.assertNotNull(status.getStatus());
        Assertions.assertEquals(status.getStatus(), StatusCode.BLOCKED);
    }

    @Test
    @Transactional
    public void findStatusByUserId1AndUserId2_returnStatusNotNull() {

        Optional<Status> statusUser = statusRepository.findByUserToAndUserFrom(1L, 2L);

        Assertions.assertNotNull(statusUser.get());
    }

    @Test
    @Transactional
    public void gegListUserIdByUserStatus_returnStatusListNotNull() {

        List<Long> statusList = statusRepository.getUserListByStatus(StatusCode.FRIEND.toString());

        Assertions.assertNotNull(statusList);
        Assertions.assertEquals(statusList.size(), 3);
    }

    @Test
    @Transactional
    public void getListUserIdByStatusFriend_returnUserList() {

        List<Long> userList = statusRepository.getUserListByStatusFriend(1L, StatusCode.FRIEND.toString());

        Assertions.assertNotNull(userList);
        Assertions.assertEquals(userList.size(), 1);
    }

    @Test
    @Transactional
    public void countByUserId1AndStatus_returnCountNotNull() {

        Integer count = statusRepository.countByUserToAndStatus(1L, StatusCode.FRIEND);

        Assertions.assertEquals(count, 1);
    }

    @Test
    @Transactional
    public void deleteById_returnStatusIsEmpty() {

        Status status1 = new Status();
        status1.setStatus(StatusCode.NONE);
        statusRepository.save(status1);

        statusRepository.deleteById(status1.getId());
        Optional<Status> status = statusRepository.findById(14L);

        org.assertj.core.api.Assertions.assertThat(status).isEmpty();
    }
}
