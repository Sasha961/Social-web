package com.example.model;

import com.example.dto.CommentTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "like_tab")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    //ID автора комментария
    @Column(name = "author_id")
    private Long authorId;

    //Дата создания лайка
    @Column(name = "time_created")
    private LocalDateTime timeCreated;

    //ID поста или комментария, к которому принадлежит лайк
    @Column(name = "item_id")
    private UUID itemId;

    //'Тип лайка: POST - лайк на пост, COMMENT- лайк на комментарий'
    @Enumerated(EnumType.STRING)
    @Column(name = "like_type")
    private CommentTypeEnum likeType;

    //Тип реакции лайка
    @Column(name = "reaction_type")
    private String reactionType;
}
