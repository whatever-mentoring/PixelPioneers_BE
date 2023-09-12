package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Pose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoseJPARepository extends JpaRepository<Pose, Integer> {
}

