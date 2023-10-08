package ru.skillbox.group39.socialnetwork.notifications.dto.notify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Artem Lebedev | 20/09/2023 - 11:42 <p><p>
 * Заполняется на основе данных из базы users<p>
 * id<p>
 * firstName<p>
 * lastName<p>
 * photo<p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String photo;

	@Override
	public String toString() {
		return "\nAuthor" +
				"\n{" +
				"\nid             :'" + id + '\'' +
				"\nFirst name     :'" + firstName + '\'' +
				"\nLast name      :'" + lastName + '\'' +
				"\nPhoto          :'" + photo + '\'' +
				"\n}";
	}
}
