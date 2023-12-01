package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumJPARepository extends JpaRepository<Album, Integer> {
}