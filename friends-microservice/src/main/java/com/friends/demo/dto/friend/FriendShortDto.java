package com.friends.demo.dto.friend;
import com.friends.demo.dto.Enum.StatusUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@Schema(description = "Dto получения параметров дружбы")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FriendShortDto {

    public String id;

    public boolean deleted;

    @Schema(description = "Статус текущих отношений")
    public StatusUser statusCode;

    @Schema(description = "Идентификатор пользователя-партнера")
    public String friendID;

    @Schema(description = "Статус предшествующих отношений")
    public StatusUser previousStatusCode;

    @Schema(description = "Рейтинг пользователя, рекомендуемого в друзья")
    public Integer rating;
}
