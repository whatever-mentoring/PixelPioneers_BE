package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.*;
import com.example.PixelPioneers.Service.AlbumService;
import com.example.PixelPioneers.config.auth.CustomUserDetails;
import com.example.PixelPioneers.config.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"사진첩 API"})
@RequiredArgsConstructor
@RestController
public class AlbumRestController {

    private final AlbumService albumService;

    /**
     * 사진첩 생성
     */
    @PostMapping(value = "/albums", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value="사진첩 생성", notes = "입력 해야하는 값: name, userIdList, file")
    public ResponseEntity<?> albumAdd(@RequestPart @Valid AlbumRequest.AlbumAddDTO requestDTO, @RequestPart MultipartFile file, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception {
        albumService.addAlbum(requestDTO, file, userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    /**
     * 로그인 한 사용자의 사진첩 전체 조회
     */
    @GetMapping("/albums")
    @ApiOperation(value="로그인 한 사용자의 사진첩 전체 조회")
    public ResponseEntity<?> albumListByUser(@RequestParam(value = "page", defaultValue = "0") Integer page, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<AlbumResponse.AlbumDTO> responseDTOs = albumService.findAlbumListByUser(userDetails.getUser(), page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    /**
     * 로그인 한 사용자의 사진첩 1개의 사진 전체 조회
     */
    @GetMapping("/albums/{id}")
    @ApiOperation(value="로그인 한 사용자의 사진첩 1개의 사진 전체 조회")
    @ApiImplicitParam(name = "id",value = "사진첩 아이디")
    public ResponseEntity<?> photoList(@PathVariable int id, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        PhotoResponse.PhotoListDTO responseDTOs = albumService.findPhotoList(id, page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    /**
     * 1개의 사진첩 수정
     */
    @PutMapping(value = "/albums/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value="1개의 사진첩 수정", notes = "포즈를 랜덤으로 1개 조회합니다. 입력 해야하는 값: name, file")
    @ApiImplicitParam(name = "id",value = "사진첩 아이디")
    public ResponseEntity<?> albumUpdate(@PathVariable int id, @RequestPart @Valid AlbumRequest.AlbumUpdateDTO updateDTO, @RequestPart MultipartFile file, Errors errors) throws Exception {
        AlbumResponse.AlbumDTO responseDTO = albumService.updateAlbum(id, updateDTO, file);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    /**
     * 1개의 사진첩을 공유하는 사용자 전체 조회
     */
    @GetMapping("/albums/{id}/members")
    @ApiOperation(value="1개의 사진첩을 공유하는 사용자 전체 조회", notes = "입력 해야하는 값: id")
    @ApiImplicitParam(name = "id",value = "사진첩 아이디")
    public ResponseEntity<?> albumMemberList(@PathVariable int id) {
        List<UserResponse.UserListDTO> responseDTOs = albumService.findAlbumMemberByAlbum(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    /**
     * 1개의 사진첩을 공유하는 사용자 추가
     */
    @PostMapping("/albums/{id}/members")
    @ApiOperation(value="1개의 사진첩을 공유하는 사용자 추가", notes = "입력 해야하는 값: id, userIdList")
    @ApiImplicitParam(name = "id",value = "사진첩 아이디")
    public ResponseEntity<?> albumMemberAdd(@PathVariable int id, @RequestBody @Valid AlbumRequest.AlbumMemberUpdateDTO updateDTO, Errors errors) {
        albumService.addAlbumMember(id, updateDTO);
        return ResponseEntity.ok((ApiUtils.success(null)));
    }

    /**
     * 사용자가 사진첩에서 나가기
     */
    @DeleteMapping("/albums/{id}/members")
    @ApiOperation(value="사용자가 사진첩에서 나가기", notes = "입력 해야하는 값: id")
    @ApiImplicitParam(name = "id",value = "사진첩 아이디")
    public ResponseEntity<?> albumMemberDelete(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        albumService.deleteAlbumMember(id, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
