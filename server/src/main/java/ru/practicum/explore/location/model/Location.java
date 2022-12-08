package ru.practicum.explore.location.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;
import ru.practicum.explore.event.model.Event;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
    Integer id;

    @Column(name = "location_title")
    String title;

    @Column(columnDefinition = "geometry(Point,4326)", name = "position", nullable = false)
    Point position;

    @Column(name = "location_lan", nullable = false)
    Double lat;

    @Column(name = "location_lon", nullable = false)
    Double lon;

    @Column(name = "country")
    String country;

    @Column(name = "city")
    String city;

    @Column(name = "suburb")
    String suburb;

    @Column(name = "road")
    String road;

    @Column(name = "house_number")
    String houseNumber;

    @Column(name = "display_name")
    String displayName;

    @OneToOne(mappedBy = "location")
    Event event;
}
