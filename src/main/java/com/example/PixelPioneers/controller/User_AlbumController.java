package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.User_AlbumResponse;
import com.example.PixelPioneers.Service.User_AlbumService;
import com.example.PixelPioneers.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = {"사진첩 사용자 API"})
@RequiredArgsConstructor
@RestController
public class User_AlbumController {

    private final User_AlbumService userAlbumService;

    // 해당 사진첩에 초대된 유저 전체 조회
    @GetMapping("/albums/{album_id}/users")
    @ApiOperation(value="사진첩 사용자 목록 전체 조회", notes = "{album_id}에 해당하는 사진첩의 사용자수와 사용자 목록 전체를 반환합니다.")
    @ApiImplicitParam(name = "album_id",value = "사진첩 아이디")
    public ResponseEntity<?> User_FK_find(@PathVariable int album_id){
        List<User_AlbumResponse.FindAllDTO> responseDTOs = userAlbumService.User_Album_FindBy_Fk(album_id);
        HashMap<Integer, List<User_AlbumResponse.FindAllDTO>> map = new HashMap<Integer, List<User_AlbumResponse.FindAllDTO>>();
        // 해당 앨범에 들어있는 유저 개수도 함께 전송
        map.put(responseDTOs.size(), responseDTOs);

        return ResponseEntity.ok(ApiUtils.success(map));
    }

    // 사진첩에 유저 초대. List로 유저의 id 값을 전달받아 수행한다.
    @PostMapping("/albums/{album_id}/users")
    @ApiOperation(value="사진첩 사용자 등록", notes = "{album_id}에 해당하는 사진첩에 유저를 등록합니다.")
    @ApiImplicitParam(name = "album_id",value = "사진첩 아이디")
    public ResponseEntity<?> album_user_insert(@RequestBody List<Integer> user_id_list, @PathVariable int album_id) {
        userAlbumService.create_new(user_id_list, album_id);

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
