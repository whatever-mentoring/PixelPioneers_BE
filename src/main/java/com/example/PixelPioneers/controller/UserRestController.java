package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.AlbumRequest;
import com.example.PixelPioneers.DTO.AlbumResponse;
import com.example.PixelPioneers.DTO.UserRequest;
import com.example.PixelPioneers.DTO.UserResponse;
import com.example.PixelPioneers.Service.UserService;
import com.example.PixelPioneers.config.auth.CustomUserDetails;
import com.example.PixelPioneers.config.jwt.JWTTokenProvider;
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

@Api(tags = {"유저 API"})
@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final UserService userService;

    /**
     * 이메일 중복 확인
     */
    @ApiOperation(value="이메일 중복 확인", notes = "중복된 이메일이 있는지 확인합니다. 입력 해야하는 값: emailCheckDTO")
    @PostMapping("/email/check")
    public ResponseEntity<?> emailCheck(@RequestBody @Valid UserRequest.EmailCheckDTO emailCheckDTO, Errors errors) {
        userService.emailCheck(emailCheckDTO.getEmail());
        return ResponseEntity.ok(ApiUtils.success(true));
    }

    /**
     * 닉네임 중복 확인
     */
    @ApiOperation(value="닉네임 중복 확인", notes = "중복된 닉네임이 있는지 확인합니다. 입력 해야하는 값: nicknameCheckDTO")
    @PostMapping("/nickname/check")
    public ResponseEntity<?> nicknameCheck(@RequestBody @Valid UserRequest.NicknameCheckDTO nicknameCheckDTO, Errors errors) {
        userService.nicknameCheck(nicknameCheckDTO.getNickname());
        return ResponseEntity.ok(ApiUtils.success(true));
    }

    /**
     * 회원가입
     */
    @ApiOperation(value="회원가입", notes = "swagger에서 테스트 불가")
    @PostMapping(value = "/join", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> join(@RequestPart @Valid UserRequest.JoinDTO requestDTO, Errors errors, @RequestPart MultipartFile file) throws Exception { //S3 업로드 위한 Exception
        userService.join(requestDTO, file);
        return ResponseEntity.ok().body(ApiUtils.success(true));
    }

    /**
     * 로그인
     */
    @ApiOperation(value="로그인", notes = "입력 해야하는 값: email, password")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors) {
        UserResponse.LoginDTO responseDTO = userService.login(requestDTO);
        return ResponseEntity.ok().header(JWTTokenProvider.HEADER, responseDTO.getJWTToken()).body(ApiUtils.success(responseDTO.getUserDetailDTO()));
    }

    /**
     * 카카오 로그인
     */
    @ApiOperation(value="카카오 로그인", notes = "카카오로 로그인합니다.")
    @GetMapping("/login/kakao")
    public void kakaoLogin(@RequestParam String code) {
        String access_token = userService.getKakaoAccessToken(code);
    }

    /**
     * 자동 완성 기능 사용 시, 사용자 전체 조회
     */
    @ApiOperation(value="사용자 전체 조회", notes = "자동 완성 기능에 사용하세요.")
    @PostMapping("/users")
    public ResponseEntity<?> userList(@RequestBody @Valid UserRequest.UserListDTO requestDTO, Errors errors) {
        List<UserResponse.UserListDTO> responseDTOs = userService.findUserList(requestDTO);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    /**
     * 사용자 1명 수정
     */
    @ApiOperation(value="1명의 유저 수정", notes = "입력 해야하는 값: id, nickname, file.")
    @ApiImplicitParam(name = "id",value = "사용자 아이디")
    @PutMapping(value = "/users/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> userUpdate(@PathVariable int id, @RequestPart @Valid UserRequest.UserUpdateDTO updateDTO, Errors errors, @RequestPart MultipartFile file, @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception {
        UserResponse.UserListDTO responseDTO = userService.updateUser(id, updateDTO, file, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
