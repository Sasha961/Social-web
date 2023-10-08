package ru.skillbox.group39.socialnetwork.notifications.dto.setting;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Artem Lebedev | 24/08/2023 - 17:46 <p>
 * enableLike - Разрешить события с типом LIKE <p>
 * enablePost - Разрешить события с типом POST <p>
 * enablePostComment - Разрешить события с типом POST_COMMENT <p>
 * enableCommentComment - Разрешить события с типом COMMENT_COMMENT <p>
 * enableMessage - Разрешить события с типом MESSAGE <p>
 * enableFriendRequest - Разрешить события с типом FRIEND_REQUEST <p>
 * enableFriendBirthday - Разрешить события с типом FRIEND_BIRTHDAY <p>
 * enableSendEmailMessage - Разрешить события с типом SEND_EMAIL_MESSAGE
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class SettingsDto {

    @JsonProperty("LIKE")
    private Boolean LIKE;
    @JsonProperty("POST")
    private Boolean POST;
    @JsonProperty("POST_COMMENT")
    private Boolean POST_COMMENT;
    @JsonProperty("COMMENT_COMMENT")
    private Boolean COMMENT_COMMENT;
    @JsonProperty("MESSAGE")
    private Boolean MESSAGE;
    @JsonProperty("FRIEND_REQUEST")
    private Boolean FRIEND_REQUEST;
    @JsonProperty("FRIEND_BIRTHDAY")
    private Boolean FRIEND_BIRTHDAY;
    @JsonProperty("SEND_EMAIL_MESSAGE")
    private Boolean SEND_EMAIL_MESSAGE;
}
