package ru.practicum.explore.comment.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UsefulKey implements Serializable {

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "comment_id")
    Integer commentId;

}
