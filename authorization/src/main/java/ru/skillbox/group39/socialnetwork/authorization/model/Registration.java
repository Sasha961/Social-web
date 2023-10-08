package ru.skillbox.group39.socialnetwork.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Artem Lebedev | 30/08/2023 - 11:04
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Registration {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private Boolean isDeleted;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String confirmPassword;
	private String captchaCode;
	private String captchaSecret;
}
