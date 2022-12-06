package ru.practicum.explore.event.model;

import lombok.*;
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
@Table(name = "events")
@SecondaryTable(name = "event_ratings", pkJoinColumns = @PrimaryKeyJoinColumn(name = "event_id"))
public class Event implements Comparable<Event> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Integer id;

    @Column(name = "event_annotation", nullable = false)
    private String annotation;

    @Column(name = "event_description", nullable = false)
    private String description;

    @Column(name = "event_title", nullable = false)
    private String title;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "event_paid", nullable = false)
    private Boolean paid;

    @Column(name = "event_available", nullable = false)
    private Boolean available;

    @Column(name = "event_created_on", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "event_published_on")
    private LocalDateTime publishedOn;

    @Column(name = "event_participant_limit", nullable = false)
    private Integer participantLimit;

    @Column(name = "event_request_moderation", nullable = false)
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "event_views", nullable = false)
    private Integer views;

    @OneToMany(mappedBy = "event")
    private Set<Request> requests;

    @OneToMany(mappedBy = "event")
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "compilation_id", referencedColumnName = "compilation_id")
    private Compilation compilation;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    @Column(name = "event_rating", table = "event_ratings")
    private Double rating;

    @Override
    public int compareTo(Event o) {
        return getEventDate().compareTo(o.getEventDate());
    }
/*
    @OneToMany(mappedBy = "user")
    private Set<Mark> marks;

 */
}
