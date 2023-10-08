package ru.skillbox.group39.search.dto.account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Schema
public class AccountSecureDto {

    public AccountSecureDto(Long id, String firstName, String lastName, String email, String password, String roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    Long id;
    Boolean isDeleted;
    String firstName;
    String lastName;
    String email;
    String password;
    String roles;
}
