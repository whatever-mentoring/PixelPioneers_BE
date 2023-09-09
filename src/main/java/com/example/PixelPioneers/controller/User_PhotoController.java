package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.User_PhotoResponse;
import com.example.PixelPioneers.Service.User_PhotoService;
import com.example.PixelPioneers.entity.User_Photo;
import com.example.PixelPioneers.utils.ApiUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class User_PhotoController {

    private final User_PhotoService userPhotoService;


    @GetMapping("/photos")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<User_PhotoResponse.FindAllDTO> responseDTOs = userPhotoService.findAll(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/photos/{photo_id}")
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

    @PostMapping("/albums/{album_id}/photo")
    public ResponseEntity<?> album_photo_insert(@RequestBody User_Photo userPhoto, @PathVariable int album_id) {
        User_PhotoResponse.FindByIdDTO responseDTO = userPhotoService.create_new(userPhoto, album_id);
        //return ResponseEntity.ok(ApiUtils.success(responseDTO));  -->  등록한 값 확인용
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
