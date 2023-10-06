package com.example.web.model;

import com.example.web.dto.CommentTypeEnum;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "like")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    private boolean isDeleted;

    //ID автора комментария
    private long authorId;

    //Дата создания лайка
    private LocalDateTime time;

    //ID поста или комментария, к которому принадлежит лайк
    private long itemId;

    //'Тип лайка: POST - лайк на пост, COMMENT- лайк на комментарий'
    private CommentTypeEnum type;

    //Тип реакции лайка
    private String reactionType;
}
