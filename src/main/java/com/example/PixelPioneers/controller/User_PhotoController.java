package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.User_PhotoResponse;
import com.example.PixelPioneers.DTO.User_PhotoVO;
import com.example.PixelPioneers.Service.User_PhotoService;
import com.example.PixelPioneers.entity.User_Photo;
import com.example.PixelPioneers.repository.AlbumJPARepository;
import com.example.PixelPioneers.utils.ApiUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"사진 API"})
@RequiredArgsConstructor
@RestController
public class User_PhotoController {

    private final User_PhotoService userPhotoService;
    private final AlbumJPARepository albumJPARepository;

    @GetMapping("/photos")
    @ApiOperation(value="사진 목록 전체 조회", notes = "데이터베이스 내의 사진 목록 전체를 반환합니다.")
    public ResponseEntity<?> findAll() {
        List<User_PhotoResponse.FindAllDTO> responseDTOs = userPhotoService.findAll();

        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/photos/{photo_id}")
    @ApiOperation(value="사진 조회", notes = "{photo_id}에 해당하는 사진 정보를 반환합니다.")
    @ApiImplicitParam(name = "photo_id",value = "사진 아이디")
    public ResponseEntity<?> findById(@PathVariable int photo_id) {
        User_PhotoResponse.FindByIdDTO responseDTO = userPhotoService.findById(photo_id);

        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // 사진 그냥 등록. 사진첩 x
    //@PostMapping("/photo")
    //public ResponseEntity<?> add(@RequestBody Photo photo){
    //    PhotoResponse.FindByIdDTO responseDTO = photoService.create(photo);
    //    return ResponseEntity.ok().body(ApiUtils.success(null));
    //}

    @PostMapping(value = "/albums/{album_id}/photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value="사진첩 사진 등록", notes = "{album_id}에 해당하는 사진첩에 사진을 등록합니다.")
    @ApiImplicitParam(name = "album_id",value = "사진첩 아이디")
    public ResponseEntity<?> album_photo_insert(@ModelAttribute User_PhotoVO userPhotoVO, @PathVariable int album_id) throws Exception {

        User_Photo new_user_photo = User_Photo.builder().photo_name(userPhotoVO.getPhoto_name())
                .photo_people_count(userPhotoVO.getPhoto_people_count())
                .photo_created_at(userPhotoVO.getPhoto_created_at())
                .photo_public(userPhotoVO.getPhoto_public())
                .album(albumJPARepository.findById(album_id).get())
                .build();

        User_PhotoResponse.FindByIdDTO responseDTO = userPhotoService.create_new(new_user_photo, userPhotoVO.getFile());
        //return ResponseEntity.ok(ApiUtils.success(responseDTO)); -->  등록한 값 확인용

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
