package ru.skillbox.group39.telegrambot.dto.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostWithUser {
    private String authorName;
    private String title;
    private String postText;
    private String imagePath;
    private LocalDateTime publishDate;

    @Override
    public String toString() {
        return authorName + "\n" +
                title + "\n" +
                postText + "\n" + "\n" +
                DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(publishDate) + "\n" + "\n" +
                imagePath;
    }
}
