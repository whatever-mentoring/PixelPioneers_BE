package com.example.Posewithme.Repository;

import com.example.Posewithme.Entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoJPARepository extends JpaRepository<Photo, Integer> {
}
