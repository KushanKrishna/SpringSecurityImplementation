package com.kushankrishna.SpringSecuriy.config;

import com.kushankrishna.SpringSecuriy.entity.User;
import com.kushankrishna.SpringSecuriy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserDetailsInfoService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.getByUserName(username);
        return user.map(UserInfoUserDetail::new).orElseThrow(()->new UsernameNotFoundException("Username not found"));

    }
}
