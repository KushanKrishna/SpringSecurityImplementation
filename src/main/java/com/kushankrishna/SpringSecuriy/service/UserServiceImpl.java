package com.kushankrishna.SpringSecuriy.service;

import com.kushankrishna.SpringSecuriy.dto.LoginRequestDto;
import com.kushankrishna.SpringSecuriy.dto.LoginResponseDto;
import com.kushankrishna.SpringSecuriy.dto.SaveUserRequestDto;
import com.kushankrishna.SpringSecuriy.entity.User;
import com.kushankrishna.SpringSecuriy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        return null;
    }

    @Override
    public User saveUser(SaveUserRequestDto saveUserRequestDto) {
        String password = passwordEncoder.encode(saveUserRequestDto.getPassword());
        User newUser = User.builder().userName(saveUserRequestDto.getUsername())
                .password(password)
                .email(saveUserRequestDto.getEmail()).
                roles(saveUserRequestDto.getRoles()).build();
        return this.userRepository.save(newUser);
    }
}
