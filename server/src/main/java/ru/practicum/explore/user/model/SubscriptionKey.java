package ru.practicum.explore.user.model;

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
public class SubscriptionKey implements Serializable {

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "subscriber_id")
    Integer subscriberId;
}
