package com.example.Posewithme.controller;

import com.example.Posewithme.dto.PoseResponse;
import com.example.Posewithme.service.PoseService;
import com.example.Posewithme.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PoseRESTController {
    private final PoseService poseService;

    @GetMapping("/poses")
    public ResponseEntity<?> poseListByPeopleCount(@RequestParam(value = "peopleCount", defaultValue = "0") int peopleCount) {
        PoseResponse.PoseListDTO responseDTOs = poseService.poseListByPeopleCount(peopleCount);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/poses/random")
    public ResponseEntity<?> randomPoseDetailByPeopleCount(@RequestParam(value = "peopleCount") int peopleCount) {
        PoseResponse.PoseDetailDTO responseDTO = poseService.randomPoseDetailByPeopleCount(peopleCount);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/poses/{id}")
    public ResponseEntity<?> poseDetail(@PathVariable int id) {
        PoseResponse.PoseDetailDTO responseDTO = poseService.poseDetail(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
