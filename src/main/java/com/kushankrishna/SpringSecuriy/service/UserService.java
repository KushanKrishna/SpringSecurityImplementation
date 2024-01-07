package com.kushankrishna.SpringSecuriy.service;

import com.kushankrishna.SpringSecuriy.dto.LoginRequestDto;
import com.kushankrishna.SpringSecuriy.dto.LoginResponseDto;
import com.kushankrishna.SpringSecuriy.dto.SaveUserRequestDto;
import com.kushankrishna.SpringSecuriy.entity.User;

public interface UserService {
    LoginResponseDto loginUser(LoginRequestDto loginRequestDto);

    User saveUser(SaveUserRequestDto saveUserRequestDto);
}
