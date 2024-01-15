package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    List<User> findByNicknameStartingWith(String nickname);

    List<User> findAll();
}
