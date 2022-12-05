package ru.practicum.explore.location.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import ru.practicum.explore.event.model.Event;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
    private Integer id;

    @Column(name = "location_title")
    private String title;

    @Column(columnDefinition = "geometry(Point,4326)", name = "position", nullable = false)
    private Point position;

    @Column(name = "location_lan", nullable = false)
    private Double lat;

    @Column(name = "location_lon", nullable = false)
    private Double lon;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "suburb")
    private String suburb;

    @Column(name = "road")
    private String road;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "display_name")
    private String displayName;

    @OneToOne(mappedBy = "location")
    private Event event;
}
