package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.entity.Pose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Collection;
import java.util.List;

public interface PoseJPARepository extends JpaRepository<Pose, Integer> {
    @Query("select p from Pose p where p.photo.album.id in :albumIdList and p.photo.peopleCount = :peopleCount")
    List<Pose> findByAlbumIdAndPeopleCount(@Param("albumIdList") List<Integer> albumIdList, @Param("peopleCount") int peopleCount);
}

