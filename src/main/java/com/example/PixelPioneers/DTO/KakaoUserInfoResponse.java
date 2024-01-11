package com.example.PixelPioneers.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfoResponse {
    private String id;
    private String email;
    private String nickname;
    private String image;
    private String ageRange;
    private String gender;
}
