package ru.skillbox.group39.friends.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dto получения счетчика заявок в друзья")
public class CountDto {

    @Schema(description = "Счетчик заявок в друзья")
    public Integer count;
}
