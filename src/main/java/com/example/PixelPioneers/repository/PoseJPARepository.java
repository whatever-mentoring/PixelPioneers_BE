package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.entity.User_Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoseJPARepository extends JpaRepository<Pose, Integer> {
    Pose findByUserPhoto(User_Photo userPhoto);
}

