package ru.practicum.explore.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor

public class RequestInfoDto {

     Integer id;

     String created;

     String status;

     Integer event;

     Integer requester;
}
