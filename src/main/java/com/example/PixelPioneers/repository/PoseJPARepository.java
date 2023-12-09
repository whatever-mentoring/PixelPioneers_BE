package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.entity.Pose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Collection;
import java.util.List;

public interface PoseJPARepository extends JpaRepository<Pose, Integer> {
    @Query("select p from Pose p where p.photo.album.id in :albumIdList and p.photo.peopleCount = :peopleCount")
    List<Pose> findByAlbumIdAndPeopleCount(@Param("albumIdList") List<Integer> albumIdList, @Param("peopleCount") int peopleCount);

    @Query("select p from Pose p where p.photo.album.id in :albumIdList and p.photo.peopleCount = :peopleCount")
    Page<Pose> findByAlbumIdAndPeopleCount(@Param("albumIdList") List<Integer> albumIdList, @Param("peopleCount") int peopleCount, Pageable pageable);

    @Query("select p from Pose p where p.photo.album.id in :albumIdList and p.photo.peopleCount = :peopleCount and p.photo.open = :open and p.photo.pass = :pass")
    Page<Pose> findByAlbumIdAndPeopleCountAndOpenAndPass(@Param("albumIdList") List<Integer> albumIdList, @Param("peopleCount") int peopleCount, @Param("open") boolean open, @Param("pass") String pass, Pageable pageable);

    @Query("select p from Pose p where p.photo.peopleCount = :peopleCount and p.photo.open = :open and p.photo.pass = :pass")
    List<Pose> findByPeopleCountAndOpenAndPass(@Param("peopleCount") int peopleCount, @Param("open") boolean open, @Param("pass") String pass);
}