package ru.practicum.explore.event.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class MarkKey implements Serializable {

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "event_id")
    Integer eventId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}
