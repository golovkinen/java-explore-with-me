package ru.practicum.explore.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explore.comment.enums.CommentState;
import ru.practicum.explore.user.dto.UserShortDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentModerateDto {

    private Integer id;
    private String text;
    private UserShortDto author;
    private LocalDateTime created;
    private CommentState state;
}
