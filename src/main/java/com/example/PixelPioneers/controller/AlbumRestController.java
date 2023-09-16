package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.*;
import com.example.PixelPioneers.Service.AlbumService;
import com.example.PixelPioneers.Service.UserService;
import com.example.PixelPioneers.config.auth.CustomUserDetails;
import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.config.utils.ApiUtils;
import com.example.PixelPioneers.entity.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

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
    public ResponseEntity<?> albumListByUser(@RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<AlbumResponse.AlbumDTO> responseDTOs = albumService.findAlbumListByUser(userDetails.getUser(), page);
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

    /**
     * 1개의 사진첩 수정
     */
    @PutMapping("/albums/{id}")
    public ResponseEntity<?> albumUpdate(@PathVariable int id, @RequestBody @Valid AlbumRequest.AlbumUpdateDTO updateDTO, Errors errors) {
        AlbumResponse.AlbumDTO responseDTO = albumService.updateAlbum(id, updateDTO);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    /**
     * 1개의 사진첩을 공유하는 사용자 전체 조회
     */
    @GetMapping("/albums/{id}/members")
    public ResponseEntity<?> albumMemberList(@PathVariable int id) {
        List<UserResponse.UserListDTO> responseDTOs = albumService.findAlbumMemberByAlbum(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    /**
     * 1개의 사진첩을 공유하는 사용자 추가
     */
    @PostMapping("/albums/{id}/members")
    public ResponseEntity<?> albumMemberAdd(@PathVariable int id, @RequestBody @Valid AlbumRequest.AlbumMemberUpdateDTO updateDTO, Errors errors) {
        albumService.addAlbumMember(id, updateDTO);
        return ResponseEntity.ok((ApiUtils.success(null)));
    }

    /**
     * 사용자가 사진첩에서 나가기
     */
    @DeleteMapping("/albums/{id}/members")
    public ResponseEntity<?> albumMemberDelete(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        albumService.deleteAlbumMember(id, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
