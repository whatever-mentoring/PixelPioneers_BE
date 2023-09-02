package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.AlbumResponse;
import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.Service.AlbumService;
import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/albums")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<AlbumResponse.FindAllDTO> responseDTOs = albumService.findAll(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/albums/{album_id}")
    public ResponseEntity<?> findById(@PathVariable int album_id) {
        AlbumResponse.FindByIdDTO responseDTO = albumService.findById(album_id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    @PostMapping("/album")
    public ResponseEntity<?> add(@RequestBody Album album){
        AlbumResponse.FindByIdDTO responseDTO = albumService.create(album);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
