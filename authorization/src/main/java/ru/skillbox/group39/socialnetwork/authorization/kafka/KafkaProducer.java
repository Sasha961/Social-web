package ru.skillbox.group39.socialnetwork.authorization.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${kafka.topics.auth}")
    private String topicName;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void sendMessage(String topicName, String message) {
        kafkaTemplate.send(topicName, message);
        log.info("--|> message '{}' sent to {}", message, topicName);
    }

    /**
     * Use with Kafka 2.x.x
     * Because the futures returned by this class are now CompletableFuture s instead of ListenableFuture
     * May add guava for ListenableFuture
     */

    public ListenableFuture<SendResult<String, String>> sendMessage2(String message) {
        log.info("Sending {}", message);
        return kafkaTemplate.send(topicName, message);
    }


    /**
     * Use with Kafka 3.x.x
     *
     * If yu will use this, remove '(CompletableFuture<SendResult<String, String>>)' behind 'kafkaTemplate, plz.
     *
     */

    public void sendMessage3(String message) {
        CompletableFuture<SendResult<String, String>> future = (CompletableFuture<SendResult<String, String>>) kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }

}

