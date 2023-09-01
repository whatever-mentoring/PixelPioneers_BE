package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.pose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoseRepository extends JpaRepository<pose, Integer> {
}
