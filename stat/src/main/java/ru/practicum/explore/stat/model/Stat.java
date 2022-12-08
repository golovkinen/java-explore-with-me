package ru.practicum.explore.stat.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "stats")
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id", nullable = false)
    Integer id;

    @Column(name = "stat_app", nullable = false)
    String app;

    @Column(name = "stat_uri", nullable = false)
    String uri;

    @Column(name = "stat_ip", nullable = false)
    String ip;

    @Column(name = "stat_timestamp", nullable = false)
    LocalDateTime timestamp;
}
