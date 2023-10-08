package ru.skillbox.group39.search.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.group39.search.dto.StatusCodeType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {

    String id;
    Boolean isDeleted;
    String firstName;
    String lastName;
    String email;
    String password;
    String role;
    Authority authority;
    String phone;
    String photo;
    String profileCover;
    String about;
    String city;
    String country;
    StatusCodeType statusCode;
    LocalDateTime regDate;
    LocalDateTime birthDate;
    String messagePermission;
    LocalDateTime lastOnlineTime;
    boolean isOnline;
    boolean isBlocked;
    String emojiStatus;
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
    LocalDateTime deletionDate;
}
