package ru.skillbox.group39.search.dto.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

//DTO тега

@Getter
@Setter
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagDto {

    UUID id;

    boolean isDeleted;

    //Имя тега
    String name;

}
