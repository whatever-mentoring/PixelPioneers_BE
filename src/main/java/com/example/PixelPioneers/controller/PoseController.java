package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.entity.pose;
import com.example.PixelPioneers.repository.PoseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PoseController {

    @Autowired
    PoseRepository poseRepository;

    @GetMapping("/poses")
    public List<pose> aa() {
        List<pose> poses = poseRepository.findAll();

        return poses;
    }

    @GetMapping("/poses/{id}")
    public Optional<pose> bb(@PathVariable Integer id) {
        Optional<pose> a = poseRepository.findById(id);

        return a;
    }
}

