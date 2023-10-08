package ru.skillbox.group39.search.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class Role {
    public Role(String id, String role) {
        this.id = id;
        this.role = role;
    }
    String id;
    String role;


}
