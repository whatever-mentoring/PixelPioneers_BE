package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.User_Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface User_AlbumJPARepository extends JpaRepository<User_Album, Integer> {
    @Query("select ua.album from User_Album ua where ua.user.id = :userId")
    Page<Album> findByUserId(int userId, Pageable pageable);
}
