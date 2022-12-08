package ru.practicum.explore.event.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "marks")
public class Mark {

    @EmbeddedId
    MarkKey id;

    @Column(name = "mark")
    Integer mark;

    @Column(name = "mark_created_on", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "mark_updated")
    private LocalDateTime lastUpdated;

}

