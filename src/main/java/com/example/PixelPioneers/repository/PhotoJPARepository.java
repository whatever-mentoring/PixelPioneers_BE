package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoJPARepository extends JpaRepository<Photo, Integer> {
    List<Photo> findByPeopleCount(int people_count);
}