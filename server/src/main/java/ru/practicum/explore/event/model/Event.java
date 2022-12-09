package ru.practicum.explore.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.explore.category.model.Category;
import ru.practicum.explore.comment.model.Comment;
import ru.practicum.explore.compilation.model.Compilation;
import ru.practicum.explore.event.enums.State;
import ru.practicum.explore.location.model.Location;
import ru.practicum.explore.request.model.Request;
import ru.practicum.explore.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "events")
@SecondaryTable(name = "event_ratings", pkJoinColumns = @PrimaryKeyJoinColumn(name = "event_id"))
public class Event implements Comparable<Event> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    Integer id;

    @Column(name = "event_annotation", nullable = false)
    String annotation;

    @Column(name = "event_description", nullable = false)
    String description;

    @Column(name = "event_title", nullable = false)
    String title;

    @Column(name = "event_date")
    LocalDateTime eventDate;

    @Column(name = "event_paid", nullable = false)
    Boolean paid;

    @Column(name = "event_available", nullable = false)
    Boolean available;

    @Column(name = "event_created_on", nullable = false)
    @CreationTimestamp
    LocalDateTime createdOn;

    @Column(name = "event_published_on")
    LocalDateTime publishedOn;

    @Column(name = "event_participant_limit", nullable = false)
    Integer participantLimit;

    @Column(name = "event_request_moderation", nullable = false)
    Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    State state;

    @Column(name = "event_views", nullable = false)
    Integer views;

    @OneToMany(mappedBy = "event")
    Set<Request> requests;

    @OneToMany(mappedBy = "event")
    Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "compilation_id", referencedColumnName = "compilation_id")
    Compilation compilation;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    Location location;

    @Column(name = "event_rating", table = "event_ratings")
    Double rating;

    @Override
    public int compareTo(Event o) {
        return getEventDate().compareTo(o.getEventDate());
    }
}
