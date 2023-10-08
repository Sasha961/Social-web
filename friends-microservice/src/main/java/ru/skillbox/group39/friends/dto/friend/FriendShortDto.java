package ru.skillbox.group39.friends.dto.friend;

import ru.skillbox.group39.friends.dto.enums.StatusCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@Schema(description = "Dto получения параметров дружбы")
@AllArgsConstructor
@NoArgsConstructor
public class FriendShortDto {

    public Long id;

    public boolean isDeleted;

    @Schema(description = "Статус текущих отношений")
    public StatusCode statusCode;

    @Schema(description = "Идентификатор пользователя-партнера")
    public Long friendID;

    @Schema(description = "Статус предшествующих отношений")
    public StatusCode previousStatusCode;

    @Schema(description = "Рейтинг пользователя, рекомендуемого в друзья")
    public Integer rating;
}
