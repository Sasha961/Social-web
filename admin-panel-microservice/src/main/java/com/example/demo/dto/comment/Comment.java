package com.example.demo.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    private boolean  isDeleted;

    private CommentTypeEnum commentType;

    // Время создания комментария
    private LocalDateTime time;

    // Время изменения комментария
    private LocalDateTime timeChanged;

    // ID автора комментария
    private long authorId;

    // ID родителя, к которому был оставлен комментарий
    private long parentId;

    //Текст комментария
    private String commentText;

    //ID поста, к которому относится комментарий
    private long postId;

    //Заблокирован ли?
    private boolean isBlocked;

    //Количество лайков комментария
    private long likeAmount;

    // Это твой лайк?
    private boolean myLike;

    //Количество комментариев
    private long commentsCount;

    //Путь к изображению
    private String imagePath;
}
