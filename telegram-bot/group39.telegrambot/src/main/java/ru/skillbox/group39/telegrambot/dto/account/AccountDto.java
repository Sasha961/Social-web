package ru.skillbox.group39.telegrambot.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class AccountDto {

    Long id;
    Boolean isDeleted;
    String firstName;
    String lastName;
    String email;
    String password;
    String roles;
    String authority;
    String phone;
    String photo;
    String profileCover;
    String about;
    String city;
    String country;
    String gender;
    StatusCodeType statusCode;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime regDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime birthDate;
    String messagePermission;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime lastOnlineTime;
    Boolean isOnline;
    Boolean isBlocked;
    String emojiStatus;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime createdOn;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime updatedOn;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime deletionDate;

    @Override
    public String toString() {
        return "First Name: " + firstName + '\n' +
                "Last Name:" + lastName + '\n' +
                "Email: " + email + '\n' +
                "Phone: " + phone + '\n' +
                "About: " + about + '\n' +
                "Photo: " + photo + '\n' +
                "City: " + city + '\n' +
                "Country: " + country + '\n' +
                "Gender: " + gender + '\n' +
                "Reg Date: " + regDate + '\n' +
                "Birth Date: " + birthDate + '\n' +
                "Last Online Time: " + LocalDateTime.now();
    }
}
