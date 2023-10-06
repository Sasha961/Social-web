package com.friends.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String identification_id;

    @Column(name = "user_id")
    String userId;

    Integer rating;

    @Column(name = "user_history")
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    List<History> userHistory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "friends_id")
    Friends friends;


}
