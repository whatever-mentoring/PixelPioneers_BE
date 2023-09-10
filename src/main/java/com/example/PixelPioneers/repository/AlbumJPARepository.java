package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumJPARepository extends JpaRepository<Album, Integer> {
}
