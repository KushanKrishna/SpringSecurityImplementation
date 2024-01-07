package com.kushankrishna.SpringSecuriy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    String message;
    HttpStatus httpStatus;
    int statusCode;
    String jwtToken;
}
