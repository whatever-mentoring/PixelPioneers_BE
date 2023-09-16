package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.PhotoRequest;
import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.Service.AlbumService;
import com.example.PixelPioneers.Service.PhotoService;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.config.utils.ApiUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PhotoRestController {

    private final AlbumService albumService;
    private final PhotoService photoService;


    /**
     * 사진 생성
     */
    @PostMapping("/albums/{id}/photos")
    public ResponseEntity<?> addPhoto(@PathVariable int id, @RequestBody @Valid PhotoRequest.PhotoAddDTO requestDTO, Errors erros) {
        photoService.addPhoto(id, requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

//    @GetMapping("/photos")
//    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
//        List<PhotoResponse.FindAllDTO> responseDTOs = photoService.findAll(page);
//        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
//    }
//
//    @GetMapping("/photos/{photo_id}")
//    public ResponseEntity<?> findById(@PathVariable int photo_id) {
//        PhotoResponse.FindByIdDTO responseDTO = photoService.findById(photo_id);
//        return ResponseEntity.ok(ApiUtils.success(responseDTO));
//    }


//    @PostMapping("/albums/{album_id}/photo")
//    public ResponseEntity<?> album_photo_insert(@RequestBody Photo photo, @PathVariable int album_id) {
//        PhotoResponse.FindByIdDTO responseDTO = photoService.create_new(photo, album_id);
//        //return ResponseEntity.ok(ApiUtils.success(responseDTO));  -->  등록한 값 확인용
//        return ResponseEntity.ok().body(ApiUtils.success(null));
//    }
}
