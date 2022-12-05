package ru.practicum.explore.stat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stats")
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id", nullable = false)
    private Integer id;

    @Column(name = "stat_app", nullable = false)
    private String app;

    @Column(name = "stat_uri", nullable = false)
    private String uri;

    @Column(name = "stat_ip", nullable = false)
    private String ip;

    @Column(name = "stat_timestamp", nullable = false)
    private LocalDateTime timestamp;
}
