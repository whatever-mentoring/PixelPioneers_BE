package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Pose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoseJPARepository extends JpaRepository<Pose, Integer> {
}

