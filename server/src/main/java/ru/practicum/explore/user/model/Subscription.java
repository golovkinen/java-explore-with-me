package ru.practicum.explore.user.model;

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
@Table(name = "subscriptions")
public class Subscription {

    @EmbeddedId
    SubscriptionKey id;

    @Column(name = "subscr_created_on", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

}

