package com.example.demo.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//ID заблокированных аккаунтов авторов постов

@Data
@Schema
public class BlockedIds {

    String blockedIds;
}
