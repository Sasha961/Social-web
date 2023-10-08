package ru.skillbox.group39.socialnetwork.notifications.model.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Artem Lebedev | 13/09/2023 - 23:49
 */

@Data
@Entity(name = "author")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "author_of_notification")
public class AuthorModel {

	@Id
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "photo")
	private String photo;

}
