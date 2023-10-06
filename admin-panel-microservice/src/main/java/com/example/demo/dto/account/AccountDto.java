package com.example.demo.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.security.Timestamp;
import java.time.LocalDateTime;


@Data
@Schema
public class AccountDto {
    String id;

    boolean isDeleted;

    String firstName;

    String lastName;

    String email;

    String password;

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

    Timestamp deletionTimestamp;





}
