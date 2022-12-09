package ru.practicum.explore.compilation.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.explore.event.model.Event;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compilations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id", nullable = false)
    Integer id;

    @Column(name = "compilation_title", nullable = false)
    String title;

    @Column(name = "compilation_created_on", nullable = false)
    @CreationTimestamp
    LocalDateTime createdOn;

    @Column(name = "compilation_pinned", nullable = false)
    Boolean pinned;

    @OneToMany(mappedBy = "compilation")
    Set<Event> events;

}
