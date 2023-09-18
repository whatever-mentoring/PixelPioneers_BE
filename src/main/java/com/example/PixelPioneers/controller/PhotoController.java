package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.DTO.PhotoRequest;
import com.example.PixelPioneers.Service.PhotoService;
import com.example.PixelPioneers.config.utils.ApiUtils;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.repository.AlbumJPARepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"사진 API"})
@RequiredArgsConstructor
@RestController
public class PhotoController {

    private final PhotoService photoService;
    private final AlbumJPARepository albumJPARepository;

    @GetMapping("/photos")
    @ApiOperation(value="사진 전체 조회", notes = "데이터베이스에 저장된 사진 전체 목록을 반환합니다.")
    public ResponseEntity<?> findAll() {
        List<PhotoResponse.FindAllDTO> responseDTOs = photoService.findAll();

        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/photos/{id}")
    @ApiOperation(value="사진 조회", notes = "{id}에 해당하는 사진의 정보를 반환합니다.")
    @ApiImplicitParam(name = "id",value = "사진 아이디")
    public ResponseEntity<?> findById(@PathVariable int id) {
        PhotoResponse.FindByIdDTO responseDTO = photoService.findById(id);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping(value = "/albums/{album_id}/photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value="사진첩에 사진 등록", notes = "{album_id}에 해당하는 사진첩에 사진을 등록합니다.")
    @ApiImplicitParam(name = "album_id",value = "사진첩 아이디")
    public ResponseEntity<?> album_insert_photo(@ModelAttribute PhotoRequest photoRequest, @PathVariable int album_id) throws Exception {
        PhotoResponse.FindByIdDTO responseDTO = photoService.create_new(photoRequest, album_id);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));

        //return ResponseEntity.ok().body(ApiUtils.success(null)); -->  등록한 값 확인용
    }

    @PostMapping("/photos/{id}")
    @ApiOperation(value="사진 공개범위 변경", notes = "{id}에 해당하는 사진이 현재 전체공개라면 비공개로, 비공개라면 전체공개로 변경")
    @ApiImplicitParam(name = "id",value = "사진 아이디")
    public ResponseEntity<?> updateById(@PathVariable int id){
        PhotoResponse.FindByIdDTO photo = photoService.updateById(id);

        return ResponseEntity.ok().body(ApiUtils.success(photo));
    }

    @DeleteMapping("/photos/{id}")
    @ApiOperation(value="사진 삭제", notes = "{id}에 해당하는 사진을 삭제합니다.")
    @ApiImplicitParam(name = "id",value = "사진 아이디")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        photoService.deleteById(id);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
