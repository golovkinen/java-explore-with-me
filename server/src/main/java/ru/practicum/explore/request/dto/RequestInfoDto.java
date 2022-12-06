package ru.practicum.explore.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfoDto {

    private Integer id;

    private String created;

    private String status;

    private Integer event;

    private Integer requester;
}
