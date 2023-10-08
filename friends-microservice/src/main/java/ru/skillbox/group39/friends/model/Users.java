package ru.skillbox.group39.friends.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String identification_id;
    @Column(name = "user_id")
    Long userId;
    Integer rating;
    Long age;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    String city;
    String country;
    String email;
    String photo;
    Boolean isBlocked;
    Boolean isDeleted;
}
