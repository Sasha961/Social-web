package Controllers;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;


@SpringBootApplication
public class MessageApplication {


    public static void main(String[] args) {

        SpringApplication.run(MessageApplication.class, args);
    }




}
