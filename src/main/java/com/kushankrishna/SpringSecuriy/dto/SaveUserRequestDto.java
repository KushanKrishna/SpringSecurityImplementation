package com.kushankrishna.SpringSecuriy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserRequestDto {
    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be blank")
    String username;
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    String password;
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    String email;
    @NotNull(message = "roles cannot be null")
    @NotBlank(message = "roles cannot be blank")
    String roles;

}
