package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoJPARepository extends JpaRepository<Photo, Integer> {
    @Query("select p from Photo p where p.album.id = :albumId")
    Page<Photo> findByAlbumId(int albumId, Pageable pageable);

    @Query("select p from Photo p where p.open =:open and p.pass = :pass")
    Page<Photo> findByOpenAndPass(@Param("open") boolean open, @Param("pass") String pass, Pageable pageable);
}