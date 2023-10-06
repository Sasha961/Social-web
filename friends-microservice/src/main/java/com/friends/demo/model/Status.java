package com.friends.demo.model;

import com.friends.demo.dto.Enum.StatusUser;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id1")
    String userId1;

    @Column(name = "user_id2")
    String userId2;

    StatusUser status;

    StatusUser previousStatusCode;

    boolean deleted;
}
