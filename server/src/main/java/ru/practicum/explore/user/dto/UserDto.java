package ru.practicum.explore.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    Integer id;
    @NotNull
    @NotBlank
    @Email(message = "Please enter a valid e-mail address")
    String email;
    @NotNull
    @NotBlank
    @Size(max = 200)
    String name;
    Boolean allowSubscription;
}
