package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.AlbumResponse;
import com.example.PixelPioneers.DTO.Photo_AlbumResponse;
import com.example.PixelPioneers.Service.AlbumService;
import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.config.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlbumRestController {

    private final AlbumService albumService;

    @GetMapping("/albums")
    public ResponseEntity<?> albumsList(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<AlbumResponse.FindAllDTO> responseDTOs = albumService.findAll(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/albums/{album_id}")
    public ResponseEntity<?> findById(@PathVariable int album_id) {
        AlbumResponse.FindByIdDTO responseDTO = albumService.findById(album_id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/albums")
    public ResponseEntity<?> add(@RequestBody Album album){
        AlbumResponse.FindByIdDTO responseDTO = albumService.create(album);
        // return ResponseEntity.ok(ApiUtils.success(responseDTO));  --> 등록한 값 확인용
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // 해당 album에 들어있는 사진 전체 조회
    @GetMapping("/albums/{album_id}/photos")
    public ResponseEntity<?> Photo_FK_find(@PathVariable int album_id){
        List<Photo_AlbumResponse.FindAllDTO> responseDTOs = albumService.Photo_FindBy_Fk(album_id);
        HashMap<Integer, List<Photo_AlbumResponse.FindAllDTO>> map = new HashMap<Integer, List<Photo_AlbumResponse.FindAllDTO>>();
        // 해당 앨범에 들어있는 사진 개수도 함께 전송
        map.put(responseDTOs.size(), responseDTOs);
        return ResponseEntity.ok(ApiUtils.success(map));
    }
}
