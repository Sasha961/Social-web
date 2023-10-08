package ru.skillbox.group39.socialnetwork.notifications.model.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Artem Lebedev | 16/09/2023 - 22:01
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notifications_settings")
public class SettingsModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonProperty("LIKE")
	@Column(name = "LIKE")
	private Boolean LIKE;
	@Column(name = "POST")
	@JsonProperty("POST")
	private Boolean POST;
	@Column(name = "POST_COMMENT")
	@JsonProperty("POST_COMMENT")
	private Boolean POST_COMMENT;
	@Column(name = "COMMENT_COMMENT")
	@JsonProperty("COMMENT_COMMENT")
	private Boolean COMMENT_COMMENT;
	@Column(name = "MESSAGE")
	@JsonProperty("MESSAGE")
	private Boolean MESSAGE;
	@Column(name = "FRIEND_REQUEST")
	@JsonProperty("FRIEND_REQUEST")
	private Boolean FRIEND_REQUEST;
	@Column(name = "FRIEND_BIRTHDAY")
	@JsonProperty("FRIEND_BIRTHDAY")
	private Boolean FRIEND_BIRTHDAY;
	@Column(name = "SEND_EMAIL_MESSAGE")
	@JsonProperty("SEND_EMAIL_MESSAGE")
	private Boolean ENABLE_EMAIL_MESSAGE;
	@Column(name = "user_id")
	private Long userId;

	public SettingsModel(Long userId) {
		this.userId = userId;
	}
}
