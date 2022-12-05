package ru.practicum.explore.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "users")
@SecondaryTable(name = "user_ratings", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "allow_subscription", nullable = false)
    private Boolean allowSubscription;

    @Column(name = "user_rating", table = "user_ratings")
    private Double rating;

    @OneToMany(mappedBy = "user")
    private Set<Event> events;

    @OneToMany(mappedBy = "user")
    private Set<Request> requests;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;
/*
    @OneToMany(mappedBy = "user")
    private Set<Mark> marks;

    @OneToMany(mappedBy = "user")
    private Set<Usefulness> useful;



    @ManyToOne(fetch = FetchType.LAZY)
    private User subscriber;

    @OneToMany(mappedBy = "user")
    private Set<User> subscriptions;

 */

}
