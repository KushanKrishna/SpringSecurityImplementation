package com.kushankrishna.SpringSecuriy.repository;

import com.kushankrishna.SpringSecuriy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> getByUserName(String username);
}
