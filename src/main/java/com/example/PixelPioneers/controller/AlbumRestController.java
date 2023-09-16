package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.AlbumRequest;
import com.example.PixelPioneers.DTO.AlbumResponse;
import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.DTO.Photo_AlbumResponse;
import com.example.PixelPioneers.Service.AlbumService;
import com.example.PixelPioneers.config.auth.CustomUserDetails;
import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.config.utils.ApiUtils;
import com.example.PixelPioneers.entity.Photo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Api(tags = {"사진첩 API"})
@RequiredArgsConstructor
@RestController
public class AlbumRestController {

    private final AlbumService albumService;

    /**
     * 사진첩 생성
     */
    @PostMapping("/albums")
    public ResponseEntity<?> albumAdd(@RequestBody @Valid AlbumRequest.AlbumAddDTO requestDTO, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails){
        albumService.addAlbum(requestDTO, userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /**
     * 로그인 한 사용자의 사진첩 전체 조회
     */
    @GetMapping("/albums")
    public ResponseEntity<?> albumsListByUser(@RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<AlbumResponse.AlbumListDTO> responseDTOs = albumService.findAlbumListByUser(userDetails.getUser(), page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    /**
     * 로그인 한 사용자의 사진첩 1개의 사진 전체 조회
     */
    @GetMapping("/albums/{id}")
    public ResponseEntity<?> photoList(@PathVariable int id, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PhotoResponse.PhotoListDTO> responseDTOs = albumService.findPhotoList(id, page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

//    @GetMapping("/albums/{id}/members")
//    public ResponseEntity<?> memberListByAlbum (@)
}
