package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.User_Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface User_PhotoJPARepository extends JpaRepository<User_Photo, Integer> {
    List<User_Photo> findByPeopleCount(int photo_people_count);
}