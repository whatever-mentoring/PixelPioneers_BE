package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.PoseResponse;
import com.example.PixelPioneers.DTO.User_PhotoResponse;
import com.example.PixelPioneers.Service.PoseService;
import com.example.PixelPioneers.utils.ApiUtils;
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
public class PoseController {
    private final PoseService poseService;

    // 인원수에 해당하는 포즈 반환. 즉, 사진 리스트 반환
    @GetMapping("/poses")
    @ApiImplicitParam(name = "peopleCount",value = "인원수")
    @ApiOperation(value="특정 인원수 포즈 조회", notes = "특정 인원수에 해당하는 전체공개 포즈 목록 전체를 반환합니다. (0명: 포즈 전체 반환)")
    public ResponseEntity<?> poseListByPeopleCount(@RequestParam(value = "peopleCount", defaultValue = "0") int peopleCount) {
        List<User_PhotoResponse.FindAllDTO> poses = poseService.poseListByPeopleCount(peopleCount);
        return ResponseEntity.ok(ApiUtils.success(poses));
    }

    @GetMapping("/manager/poses")
    @ApiOperation(value="전체공개 포즈 목록 조회", notes = "전체공개인 포즈 목록을 반환합니다.")
    public ResponseEntity<?> poseList() {
        PoseResponse.PoseListDTO responseDTOs = poseService.manager_poseList();
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    //@GetMapping("/poses/random")
    //@ApiImplicitParam(name = "peopleCount",value = "인원수")
    //@ApiOperation(value="특정 인원수 포즈 랜덤 조회", notes = "peopleCount명에 해당하는 포즈 목록 중 랜덤한 하나의 포즈를 반환합니다.")
    //public ResponseEntity<?> randomPoseDetailByPeopleCount(@RequestParam(value = "peopleCount") int peopleCount) {
    //    PoseResponse.PoseDetailDTO responseDTO = poseService.randomPoseDetailByPeopleCount(peopleCount);
    //    return ResponseEntity.ok(ApiUtils.success(responseDTO));
    //}

    //@GetMapping("/poses/{id}")
    //@ApiImplicitParam(name = "id",value = "포즈 아이디")
    //@ApiOperation(value="전체공개 포즈 개별 조회", notes = "{id}에 해당하는 전체공개 포즈 정보를 반환합니다.")
    //public ResponseEntity<?> poseDetail(@PathVariable int id) {
    //    PoseResponse.PoseDetailDTO responseDTO = poseService.poseDetail(id);
    //    return ResponseEntity.ok(ApiUtils.success(responseDTO));
    //}
}
