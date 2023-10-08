package ru.skillbox.group39.telegrambot.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDtoLite {

    String firstName;
    String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName + '\n';
    }
}
