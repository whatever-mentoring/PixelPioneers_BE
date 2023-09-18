package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.DTO.PhotoRequest;
import com.example.PixelPioneers.Service.PhotoService;
import com.example.PixelPioneers.config.utils.ApiUtils;
import com.example.PixelPioneers.repository.AlbumJPARepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api(tags = {"사진 API"})
@RequiredArgsConstructor
@RestController
public class PhotoRestController {

    private final PhotoService photoService;
    private final AlbumJPARepository albumJPARepository;

    /**
     * 사진 생성
     */
    @PostMapping(value = "/albums/{id}/photos", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value="사진 생성", notes = "{id}에 해당하는 사진첩에 사진을 생성합니다. 입력 해야하는 값: id, name, peopleCount, created_at, open")
    @ApiImplicitParam(name = "id",value = "사진 아이디")
    public ResponseEntity<?> addPhoto(@PathVariable int id, @RequestPart @Valid PhotoRequest.PhotoAddDTO requestDTO, @RequestPart MultipartFile file, Errors erros) throws Exception {
        photoService.addPhoto(id, requestDTO, file);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PostMapping("/photos/{id}")
    @ApiOperation(value="사진 공개범위 변경", notes = "{id}에 해당하는 사진이 현재 전체공개라면 비공개로, 비공개라면 전체공개로 변경")
    @ApiImplicitParam(name = "id",value = "사진 아이디")
    public ResponseEntity<?> updateById(@PathVariable int id){
        PhotoResponse.FindByIdDTO photo = photoService.updateById(id);

        return ResponseEntity.ok().body(ApiUtils.success(photo));
    }
}
