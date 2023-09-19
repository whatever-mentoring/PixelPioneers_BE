package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.DTO.PhotoRequest;
import com.example.PixelPioneers.Service.PhotoService;
import com.example.PixelPioneers.config.auth.CustomUserDetails;
import com.example.PixelPioneers.config.utils.ApiUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api(tags = {"사진 API"})
@RequiredArgsConstructor
@RestController
public class PhotoRestController {

    private final PhotoService photoService;

    /**
     * 사진 생성
     */
    @PostMapping(value = "/albums/{id}/photos", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value="사진 생성", notes = "{id}에 해당하는 사진첩에 사진을 생성합니다. 입력 해야하는 값: id, name, peopleCount, created_at, open")
    @ApiImplicitParam(name = "id",value = "사진 아이디")
    public ResponseEntity<?> addPhoto(@PathVariable int id, @RequestPart @Valid PhotoRequest.PhotoAddDTO requestDTO, @RequestPart MultipartFile file, @AuthenticationPrincipal CustomUserDetails userDetails, Errors erros) throws Exception {
        photoService.addPhoto(id, requestDTO, file, userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /**
     * 사진을 올린 사용자만 사진 삭제
     */
    @DeleteMapping("/albums/{albumId}/photos/{photoId}")
    @ApiOperation(value="사진을 올린 사용자만 사진 삭제", notes = "입력 해야하는 값: albumId, photoId")
    public ResponseEntity<?> deletePhoto(@PathVariable int albumId, @PathVariable int photoId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        photoService.deletePhoto(photoId, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
