package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.Service.PhotoService;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.utils.ApiUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PhotoController {

    private final PhotoService photoService;


    @GetMapping("/photos")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PhotoResponse.FindAllDTO> responseDTOs = photoService.findAll(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/photos/{photo_id}")
    public ResponseEntity<?> findById(@PathVariable int photo_id) {
        PhotoResponse.FindByIdDTO responseDTO = photoService.findById(photo_id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // 사진 그냥 등록. 사진첩 x
    //@PostMapping("/photo")
    //public ResponseEntity<?> add(@RequestBody Photo photo){
    //    PhotoResponse.FindByIdDTO responseDTO = photoService.create(photo);
    //    return ResponseEntity.ok().body(ApiUtils.success(null));
    //}

    @PostMapping("/albums/{album_id}/photo")
    public ResponseEntity<?> album_photo_insert(@RequestBody Photo photo, @PathVariable int album_id) {
        PhotoResponse.FindByIdDTO responseDTO = photoService.create_new(photo, album_id);
        //return ResponseEntity.ok(ApiUtils.success(responseDTO));  -->  등록한 값 확인용
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
