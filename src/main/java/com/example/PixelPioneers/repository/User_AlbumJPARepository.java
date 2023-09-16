package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.User;
import com.example.PixelPioneers.entity.User_Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface User_AlbumJPARepository extends JpaRepository<User_Album, Integer> {
    @Query("select ua.album from User_Album ua where ua.user.id = :userId")
    Page<Album> findAlbumByUserId(int userId, Pageable pageable);

    @Query("select ua.user from User_Album ua where ua.album.id = :albumId")
    List<User> findUserByAlbum(int albumId);

    @Modifying
    @Transactional
    @Query("delete from User_Album ua where ua.album.id = :albumId and ua.user.id = :userId")
    void deleteByUserIdAndAlbumId(@Param("albumId") int albumId, @Param("userId") int userId);
}
