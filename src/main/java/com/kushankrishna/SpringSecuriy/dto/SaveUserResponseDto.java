package com.kushankrishna.SpringSecuriy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserResponseDto {
    @JsonProperty(index = 1)
    String username;
    @JsonProperty(index = 4)
    String message;
    @JsonProperty(index = 2)
    String email;
    @JsonProperty(index = 3)
    String roles;
}
