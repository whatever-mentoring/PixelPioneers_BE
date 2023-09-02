package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoJPARepository extends JpaRepository<Photo, Integer> {
}