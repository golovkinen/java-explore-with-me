package ru.practicum.explore.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationInfoDto {

        private Integer id;
        private String title;
        private Double lat;
        private Double lon;
        private String address;
}
