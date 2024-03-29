package com.example.PixelPioneers.controller;

import com.amazonaws.Response;
import com.example.PixelPioneers.DTO.AdminRequest;
import com.example.PixelPioneers.DTO.AdminResponse;
import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.DTO.UserResponse;
import com.example.PixelPioneers.Service.AdminService;
import com.example.PixelPioneers.config.jwt.JWTTokenProvider;
import com.example.PixelPioneers.config.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.RequestDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"관리자 API"})
@RequiredArgsConstructor
@RestController
public class AdminRestController {

    private final AdminService adminService;

    @GetMapping("/admin/review")
    @ApiOperation(value="사용자가 공개 요청한 사진 목록")
    public ResponseEntity<?> requestPhotoList(@RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        List<PhotoResponse.PhotoListDTO> responseDTOs =  adminService.findRequestPhotoList(page);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/admin/review/photoes/{photoId}")
    @ApiOperation(value="사용자가 공개 요청한 사진 1개 조회", notes = "입력 해야하는 값: id")
    public ResponseEntity<?> requestPhotoDetail(@PathVariable int photoId) {
        PhotoResponse.PhotoDetailDTO responseDTO =  adminService.findRequestPhotoDetail(photoId);
        return ResponseEntity.ok().body(ApiUtils.success(true));
    }

    @PostMapping("/admin/review/photoes/{photoId}")
    @ApiOperation(value="사용자가 공개 요청한 사진 1개 조회", notes = "입력 해야하는 값: id")
    public ResponseEntity<?> requestPhotoReview(@PathVariable int photoId, @RequestBody AdminRequest.requestPhotoReviewDTO requestDTO) {
        adminService.reviewRequestPhoto(photoId, requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(true));
    }

    @GetMapping("/admin/update/user-info/all")
    @ApiOperation(value="모든 사용자 중 성별과 연령대 정보가 없는 유저 정보 업데이트 (동의 안 한 사람 제외)")
    public ResponseEntity<?> requestAllUserInfoUpdate() {
        adminService.updateAllUserInfoByKakao();
        return ResponseEntity.ok().body(ApiUtils.success(true));
    }

    @GetMapping("/admin/statistics")
    @ApiOperation(value="사용자 통계")
    public ResponseEntity<?> requestUserInfo() {
        List<AdminResponse.UserInfoDTO> responseDTOs = adminService.findRequestUserInfo();
        return ResponseEntity.ok().body(ApiUtils.success(responseDTOs));
    }
}
