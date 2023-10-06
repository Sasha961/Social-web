package com.friends.demo.dto.friend;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "Dto для поиска записей дружбы по условиям")
public class FriendSearchDto {


    public String id;

    public boolean isDeleted;

    @Schema(description = "Идентификатор пользователя, имеющего отношения с текущим пользователем")
    public String idFrom;

    @Schema(description = "Статус текущих отношений пользователя")
    public String statusCode;

    @Schema(description = "Идентификатор текущего пользователя")
    public String idTo;

    @Schema(description = "Статус предшествующих отношений пользователя")
    public String previousStatusCode;
}
