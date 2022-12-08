package ru.practicum.explore.comment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentInfoDto {

    Integer id;
    String text;
    String authorName;
    LocalDateTime created;
    Integer useful;
    Integer notUseful;
}
