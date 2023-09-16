package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.UserRequest;
import com.example.PixelPioneers.Service.UserService;
import com.example.PixelPioneers.config.jwt.JWTTokenProvider;
import com.example.PixelPioneers.config.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final UserService userService;

    /**
     * 이메일 중복 확인
     */
    @PostMapping("/email/check")
    public ResponseEntity<?> emailCheck(@RequestBody @Valid UserRequest.EmailCheckDTO emailCheckDTO, Errors errors) {
        userService.emailCheck(emailCheckDTO.getEmail());
        return ResponseEntity.ok(ApiUtils.success(true));
    }

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Errors errors) {
        userService.join(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(true));
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors) {
        String jwt = userService.login(requestDTO);
        return ResponseEntity.ok().header(JWTTokenProvider.HEADER, jwt).body(ApiUtils.success(true));
    }

    @GetMapping("/login/kakao")
    public void kakaoLogin(@RequestParam String code) {
        String access_token = userService.getKakaoAccessToken(code);
    }
}