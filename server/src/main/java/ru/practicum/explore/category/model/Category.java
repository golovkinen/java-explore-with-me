package ru.practicum.explore.category.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore.event.model.Event;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    Integer id;

    @Column(name = "category_name", nullable = false)
    String name;

    @OneToMany(mappedBy = "category")
    Set<Event> events;
}
