package com.friends.demo.repository;

import com.friends.demo.database.BuildingPostgresqlContainer;
import com.friends.demo.dto.Enum.Events;
import com.friends.demo.model.History;
import com.friends.demo.model.Users;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@Testcontainers
public class HistoryRepositoryTest {

    @Autowired
    HistoryRepository historyRepository;

    @ClassRule
    @Container
    public static PostgreSQLContainer<BuildingPostgresqlContainer> postgreSQLContainer = BuildingPostgresqlContainer.getInstance();

    @Before
    public void setUp() {

        History history = History.builder()
                .event(Events.ADD_USER)
                .user_id(1)
                .users(new Users())
                .build();

        History history2 = History.builder()
                .event(Events.DELETE_USER)
                .user_id(1)
                .users(new Users())
                .build();

        historyRepository.save(history);
        historyRepository.save(history2);
    }

    @Test
    @Transactional
    public void saveHistory_returnHistoryNotNull() {

        History history = History.builder()
                .users(new Users())
                .user_id(3)
                .event(Events.ADD_USER)
                .build();
        History saveHistory = historyRepository.save(history);

        Assertions.assertNotNull(saveHistory);
        org.assertj.core.api.Assertions.assertThat(saveHistory.getId()).isGreaterThan(0);
    }

    @Test
    @Transactional
    public void findById_returnHistoryNotNull() {

        History history = historyRepository.findById(3L).get();

        org.assertj.core.api.Assertions.assertThat(history).isNotNull();
    }

    @Test
    @Transactional
    public void findAll_returnHistoryListNotNull() {

        List<History> historyList = historyRepository.findAll();

        Assertions.assertNotNull(historyList);
        Assertions.assertEquals(historyList.size(), 2);
    }

    @Test
    @Transactional
    public void updateHistory_returnHistoryNotNull() {

        History history = historyRepository.findById(8L).get();
        history.setEvent(Events.DELETE_USER);
        history.setUsers(Users.builder().userId("1").build());
        historyRepository.save(history);

        Assertions.assertNotNull(history.getEvent());
        Assertions.assertEquals(history.getEvent(), Events.DELETE_USER);
    }

    @Test
    @Transactional
    public void deleteById_returnHistoryNotNull() {

        historyRepository.deleteById(1L);
        Optional<History> history = historyRepository.findById(1L);

        org.assertj.core.api.Assertions.assertThat(history).isEmpty();
    }
}
