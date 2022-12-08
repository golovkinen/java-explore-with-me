package ru.practicum.explore.comment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore.comment.enums.CommentState;
import ru.practicum.explore.user.dto.UserShortDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentModerateDto {

    Integer id;
    String text;
    UserShortDto author;
    LocalDateTime created;
    CommentState state;
}
