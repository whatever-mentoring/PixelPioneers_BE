package com.example.Posewithme.repository;

import com.example.Posewithme.entity.Pose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PoseJPARepository extends JpaRepository<Pose, Integer> {
    List<Pose> findAllByPeopleCount(int peopleCount);
}
