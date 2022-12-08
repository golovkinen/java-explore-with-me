package ru.practicum.explore.comment.model;

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
@Table(name = "usefulness")
public class Usefulness {

    @EmbeddedId
    UsefulKey id;

    @Column(name = "useful")
    Boolean useful;

    @Column(name = "vote_created_on", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "vote_updated")
    private LocalDateTime lastUpdated;


}

