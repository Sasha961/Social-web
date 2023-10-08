package com.example.model;

import com.example.dto.CommentTypeEnum;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "is_deleted")
    private Boolean  isDeleted;

    //    Тип комментария: POST - комментарий к посту, COMMENT - комментарий к
    //    комментарию, субкомментарий
    @Enumerated(EnumType.STRING)
    @Column(name = "comment_type")
    //@Type(CommentTypeEnum.COMMENT)
    private CommentTypeEnum commentType;

    // Время создания комментария
    @Column(name = "time_created")
    private LocalDateTime timeCreated;

    // Время изменения комментария
    @Column(name = "time_changed")
    private LocalDateTime timeChanged;

    // ID автора комментария
    @Column(name = "author_id")
    private Long authorId;

    // ID родителя, к которому был оставлен комментарий
    @Column(name = "parent_id")
    private UUID parentId;

    //Текст комментария
    @Column(name = "comment_text")
    private String commentText;

    //ID поста, к которому относится комментарий
    @Column(name = "post_id")
    private UUID postId;

    //Заблокирован ли?
    @Column(name = "is_blocked")
    private Boolean isBlocked;

    //Количество лайков комментария
    @Column(name = "like_amount")
    private Long likeAmount;

    // Это твой лайк?
    @Column(name = "my_like")
    private Boolean myLike;

    //Количество комментариев
    @Column(name = "comments_count")
    private Long commentsCount;

    //Путь к изображению
    @Column(name = "image_path")
    private String imagePath;
}
