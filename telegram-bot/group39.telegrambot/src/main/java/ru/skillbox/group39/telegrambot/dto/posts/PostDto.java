package ru.skillbox.group39.telegrambot.dto.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto {
    private UUID id;
    private Boolean isDeleted;
    private LocalDateTime timeCreated;
    private LocalDateTime timeChanged;
    private Long authorId;
    private String title;
//    private TypePostEnum postType;
    private String postText;
    private Boolean isBlocked;
    private Integer commentsCount;
    private String tags;
    private String reactions;
    private String myReaction;
    private Integer likeAmount;
    private Boolean myLike;
    private String imagePath;
    private LocalDateTime publishDate;

}
