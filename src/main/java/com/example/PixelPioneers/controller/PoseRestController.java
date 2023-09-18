package com.example.PixelPioneers.controller;

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

@Api(tags = {"포즈 API"})

@RequiredArgsConstructor
@RestController
public class PoseRestController {
    private final PoseService poseService;

    @GetMapping("/poses")
    @ApiImplicitParam(name = "peopleCount",value = "인원수")
    @ApiOperation(value="특정 인원수 포즈 조회", notes = "peopleCount명에 해당하는 포즈 목록 전체를 반환합니다.")
    public ResponseEntity<?> poseListByPeopleCount(@RequestParam(value = "peopleCount", defaultValue = "0") int peopleCount) {
        PoseResponse.PoseListDTO responseDTOs = poseService.poseListByPeopleCount(peopleCount);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/poses/random")
    @ApiImplicitParam(name = "peopleCount",value = "인원수")
    @ApiOperation(value="특정 인원수 포즈 랜덤 조회", notes = "peopleCount명에 해당하는 포즈 목록 중 랜덤한 하나의 포즈를 반환합니다.")
    public ResponseEntity<?> randomPoseDetailByPeopleCount(@RequestParam(value = "peopleCount") int peopleCount) {
        PoseResponse.PoseDTO responseDTO = poseService.randomPoseDetailByPeopleCount(peopleCount);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @GetMapping("/poses/{id}")
    @ApiImplicitParam(name = "id",value = "포즈 아이디")
    @ApiOperation(value="포즈 조회", notes = "{id}에 해당하는 포즈 정보를 반환합니다.")
    public ResponseEntity<?> poseDetail(@PathVariable int id) {
        PoseResponse.PoseDetailDTO responseDTO = poseService.poseDetail(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
