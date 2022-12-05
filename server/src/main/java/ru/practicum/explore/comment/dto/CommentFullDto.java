package ru.practicum.explore.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explore.comment.enums.CommentState;
import ru.practicum.explore.event.dto.EventInfoDto;
import ru.practicum.explore.user.dto.UserShortDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentFullDto {

    private Integer id;
    private String text;
    private UserShortDto author;
    private EventInfoDto event;
    private LocalDateTime created;
    private CommentState state;
    private Integer useful;
    private Integer notUseful;
}
