package ru.practicum.explore.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore.comment.model.Comment;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.request.model.Request;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
@SecondaryTable(name = "user_ratings", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    Integer id;

    @Column(name = "user_email", unique = true, nullable = false)
    String email;

    @Column(name = "user_name", nullable = false)
    String name;

    @Column(name = "allow_subscription", nullable = false)
    Boolean allowSubscription;

    @Column(name = "user_rating", table = "user_ratings")
    Double rating;

    @OneToMany(mappedBy = "user")
    Set<Event> events;

    @OneToMany(mappedBy = "user")
    Set<Request> requests;

    @OneToMany(mappedBy = "user")
    Set<Comment> comments;
}
