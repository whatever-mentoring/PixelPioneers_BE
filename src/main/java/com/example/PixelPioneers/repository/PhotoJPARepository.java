package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhotoJPARepository extends JpaRepository<Photo, Integer> {
    @Query("select p from Photo p where p.album.id = :albumId")
    Page<Photo> findByAlbumId(int albumId, Pageable pageable);
}