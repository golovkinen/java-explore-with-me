package ru.practicum.explore.comment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    Integer id;

    @Column(name = "comment_text", nullable = false)
    String text;

    @Column(name = "created", nullable = false)
    @CreationTimestamp
    LocalDateTime created;

    @Enumerated(EnumType.STRING)
    CommentState state;

    @Column(name = "comment_useful")
    Integer useful;

    @Column(name = "comment_not_useful")
    Integer notUseful;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    Event event;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    User user;

}
