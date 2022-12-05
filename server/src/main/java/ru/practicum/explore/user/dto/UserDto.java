package ru.practicum.explore.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Integer id;
    @NotNull
    @NotBlank
    @Email(message = "Please enter a valid e-mail address")
    private String email;
    @NotNull
    @NotBlank
    @Size(max = 200)
    private String name;
    private Boolean allowSubscription;
}
