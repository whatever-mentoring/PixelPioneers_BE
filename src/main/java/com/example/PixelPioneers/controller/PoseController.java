package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.response.PoseResponse;
import com.example.PixelPioneers.service.PoseService;
import com.example.PixelPioneers.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PoseController {

    private final PoseService poseService;

    @GetMapping("/poses")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PoseResponse.FindAllDTO> responseDTOs = poseService.findAll(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/poses/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        PoseResponse.FindByIdDTO responseDTO = poseService.findById(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}

