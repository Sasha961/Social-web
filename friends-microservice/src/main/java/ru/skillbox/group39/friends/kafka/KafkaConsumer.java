package ru.skillbox.group39.friends.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.skillbox.group39.friends.dto.AccountForFriends;
import ru.skillbox.group39.friends.model.Users;
import ru.skillbox.group39.friends.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "friends-topic", groupId = "${spring.application.name}")
    public void addUsers(AccountForFriends accountForFriends) {
        log.info(String.format("Json message -> %s", accountForFriends.toString()));

        if (userRepository.findByUserId(accountForFriends.getId()).isEmpty()) {
            Users users = objectMapper.convertValue(accountForFriends, Users.class);
            users.setUserId(accountForFriends.getId());
            users.setRating(0);
            users.setAge(accountForFriends.getBirthDate() != null ?
                    (long) (LocalDateTime.now().getYear() - (accountForFriends.getBirthDate()).getYear()) : null);
            userRepository.save(users);
            log.info(String.format("User save -> %s", users));
        } else {
            log.info("user exists");
        }
    }
}
