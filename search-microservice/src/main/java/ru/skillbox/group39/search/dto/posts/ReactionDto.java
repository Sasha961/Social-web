package ru.skillbox.group39.search.dto.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

//Список типов реакций

@Builder
@Getter
@Setter
@Schema
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReactionDto {

    private UUID id;

    private String reactionType;

    private long count;
}
