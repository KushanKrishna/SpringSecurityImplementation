package com.kushankrishna.SpringSecuriy.controller;

import com.kushankrishna.SpringSecuriy.constraints.Constraints;
import com.kushankrishna.SpringSecuriy.dto.LoginRequestDto;
import com.kushankrishna.SpringSecuriy.dto.LoginResponseDto;
import com.kushankrishna.SpringSecuriy.dto.SaveUserRequestDto;
import com.kushankrishna.SpringSecuriy.dto.SaveUserResponseDto;
import com.kushankrishna.SpringSecuriy.entity.User;
import com.kushankrishna.SpringSecuriy.service.JwtService;
import com.kushankrishna.SpringSecuriy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/myapp")
public class AppController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;

    /*
    Created api which can be accessed by any user without authentication.
     */
    @GetMapping("/home")
    public ResponseEntity<?> home() {
        return ResponseEntity.status(HttpStatus.OK).body(Constraints.HomePageMsg);
    }

    /*
    This api will be accessed by only persons with admin rights.
     */
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> admin() {
        return ResponseEntity.status(HttpStatus.OK).body(Constraints.AdminPageMsg);
    }

    /*
    This api will be accessed by only persons with user right
     */
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> userpage() {
        return ResponseEntity.status(HttpStatus.OK).body(Constraints.UserPageMsg);
    }

    /*
    This api can be accessed without any authentication. User must provide username and password,
    in response to which the application will return a jwt token if the authentication is successful.
    This api internally calls authenticate api.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticate(loginRequestDto));
    }
     /*
    This api can be accessed without any authentication. User must provide username and password,
    in response to which the application will return a jwt token if the authentication is successful.
     */

    @PostMapping("/authenticate")
    public LoginResponseDto authenticate(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword()));
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        if (authentication.isAuthenticated()) {
            loginResponseDto.setMessage(Constraints.LoginSuccess);
            loginResponseDto.setHttpStatus(HttpStatus.OK);
            loginResponseDto.setStatusCode(HttpStatus.OK.value());
            loginResponseDto.setJwtToken(jwtService.generateToken(loginRequestDto.getUserName()));
        } else {
            loginResponseDto.setMessage(Constraints.LoginFailed);
            loginResponseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
            loginResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        }
        return loginResponseDto;
    }

    /*
    This api can be accessed without any authentication. This api can be used to save user data in the system database.
    Note: All the fields i.e username, password, email, roles should not be blank or null.
     */
    @PostMapping("/saveuser")
    public ResponseEntity<?> saveuser(@RequestBody @Valid SaveUserRequestDto saveUserRequestDto) {
        User user = this.userService.saveUser(saveUserRequestDto);
        SaveUserResponseDto saveUserResponseDto = new SaveUserResponseDto();
        if (Objects.nonNull(user)) {
            saveUserResponseDto.setEmail(user.getEmail());
            saveUserResponseDto.setUsername(user.getUserName());
            saveUserResponseDto.setRoles(user.getRoles());
            saveUserResponseDto.setMessage(Constraints.SavedSuccess);
        } else {
            saveUserResponseDto.setMessage(Constraints.SaveFailed);
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUserResponseDto);
    }
}
