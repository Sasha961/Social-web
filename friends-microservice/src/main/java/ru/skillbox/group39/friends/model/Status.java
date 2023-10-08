package ru.skillbox.group39.friends.model;

import ru.skillbox.group39.friends.dto.enums.StatusCode;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_to")
    Long userTo;

    @Column(name = "user_from")
    Long userFrom;

    @NotNull
    @Enumerated(EnumType.STRING)
    StatusCode status;

    @NotNull
    @Enumerated(EnumType.STRING)
    StatusCode previousStatusCode;

    boolean deleted;
}
