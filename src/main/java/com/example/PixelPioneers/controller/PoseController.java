package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.PoseResponse;
import com.example.PixelPioneers.Service.PoseService;
import com.example.PixelPioneers.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"포즈 API"})
@RequiredArgsConstructor
@RestController
public class PoseController {
    private final PoseService poseService;

    // 사용자가 전체공개로 등록한 사진 중, 입력한 인원수에 해당하는 사진 조회
    @GetMapping("/poses/user")
    @ApiImplicitParam(name = "peopleCount",value = "인원수")
    @ApiOperation(value="유저포즈 조회", notes = "사용자가 전체공개로 등록한 사진 중, 입력한 인원수에 해당하는 포즈 목록을 반환합니다. (0명: 전체 반환)")
    public ResponseEntity<?> userPoseListByPeopleCount(@RequestParam(value = "peopleCount", defaultValue = "0") int peopleCount) {
        PoseResponse.PoseListDTO user_poses = poseService.userPoseListByPeopleCount(peopleCount);
        return ResponseEntity.ok(ApiUtils.success(user_poses));
    }

    @GetMapping("/poses/admin")
    @ApiImplicitParam(name = "peopleCount",value = "인원수")
    @ApiOperation(value="관리자포즈 조회", notes = "관리자가 등록한 사진 중, 입력한 인원수에 해당하는 포즈 목록을 반환합니다. (0명: 전체 반환)")
    public ResponseEntity<?> adminPoseListByPeopleCount(@RequestParam(value = "peopleCount", defaultValue = "0") int peopleCount) {
        PoseResponse.PoseListDTO admin_poses = poseService.adminPoseListByPeopleCount(peopleCount);
        return ResponseEntity.ok(ApiUtils.success(admin_poses));
    }

    @GetMapping("/poses/random")
    @ApiImplicitParam(name = "peopleCount",value = "인원수")
    @ApiOperation(value="포즈 랜덤 조회", notes = "{peopleCount}명에 해당하는 포즈 중 랜덤한 하나의 포즈를 반환합니다.")
    public ResponseEntity<?> randomPoseByPeopleCount(@RequestParam(value = "peopleCount") int peopleCount) {
        PoseResponse.PoseDTO responseDTO = poseService.randomPoseByPeopleCount(peopleCount);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}
