package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.PoseResponse;
import com.example.PixelPioneers.Service.PoseService;
import com.example.PixelPioneers.config.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PoseRestController {

    private final PoseService poseService;

    @GetMapping("/poses/random")
    public ResponseEntity<?> randomPoseDetailByPeopleCount(@RequestParam(value = "peopleCount") int peopleCount) {
        PoseResponse.PoseDTO responseDTO = poseService.randomPoseDetailByPeopleCount(peopleCount);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/poses")
    public ResponseEntity<?> PoseListByPeopleCount(@RequestParam(value = "category") String category, @RequestParam(value = "peopleCount", defaultValue = "0") int peopleCount) {
        List<PoseResponse.PoseDTO> responseDTOs = poseService.poseListByCategoryAndPeopleCount(category, peopleCount);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }
}