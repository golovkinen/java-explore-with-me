package ru.practicum.explore.comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.explore.comment.enums.CommentState;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Integer id;

    @Column(name = "comment_text", nullable = false)
    private String text;

    @Column(name = "created", nullable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    private CommentState state;

    @Column(name = "comment_useful")
    private Integer useful;

    @Column(name = "comment_not_useful")
    private Integer notUseful;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user;
/*
    @OneToMany(mappedBy = "user")
    private Set<Usefulness> useful;

 */
}
