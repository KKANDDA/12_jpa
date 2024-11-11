package com.ohgiraffers.chap06securityjwt.user.repository;

import com.ohgiraffers.chap06securityjwt.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String username);
}
