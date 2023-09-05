package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<User, Integer> {
}
