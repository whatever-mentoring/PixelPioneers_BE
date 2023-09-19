package com.example.PixelPioneers.controller;

import com.amazonaws.Response;
import com.example.PixelPioneers.DTO.PoseResponse;
import com.example.PixelPioneers.Service.PoseService;
import com.example.PixelPioneers.config.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(tags = {"포즈 API"})
@RequiredArgsConstructor
@RestController
public class PoseRestController {

    private final PoseService poseService;

    /**
     * 포즈 랜덤 조회
     */
    @GetMapping("/poses/random")
    @ApiOperation(value="포즈 랜덤 조회", notes = "포즈를 랜덤으로 1개 조회합니다. 입력 해야하는 값: peopleCount")
    @ApiImplicitParam(name = "peopleCount",value = "인원수")
    public ResponseEntity<?> randomPoseDetailByPeopleCount(@RequestParam(value = "peopleCount") int peopleCount) {
        PoseResponse.PoseDTO responseDTO = poseService.randomPoseDetailByPeopleCount(peopleCount);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    /**
     * 1. 추천 or 스페셜
     * 2. 인원 수
     * 에 따라 포즈 전체 조회
     */
    @GetMapping("/poses")
    @ApiOperation(value="포즈 전체 조회", notes = "category에 해당하는 포즈를 전체 조회합니다. 입력 해야하는 값: category, peopleCount")
    @ApiImplicitParam(name = "category",value = "ROLE_USER or ROLE_ADMIN")
    public ResponseEntity<?> PoseListByPeopleCount(@RequestParam(value = "category") String category, @RequestParam(value = "peopleCount") int peopleCount) {
        List<PoseResponse.PoseDTO> responseDTOs = poseService.poseListByCategoryAndPeopleCount(category, peopleCount);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/poses/{id}")
    @ApiOperation(value="포즈 1개 조회", notes = "{id}에 해당하는 포즈를 조회합니다. 입력 해야하는 값: id")
    @ApiImplicitParam(name = "id",value = "포즈 아이디")
    public ResponseEntity<?> PoseDetail(@PathVariable int id) {
        PoseResponse.PoseDTO responseDTO = poseService.findPoseDetail(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}