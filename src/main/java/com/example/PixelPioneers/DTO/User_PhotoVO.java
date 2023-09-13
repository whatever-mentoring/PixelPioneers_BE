package com.example.PixelPioneers.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class User_PhotoVO {

    private String photo_name;

    private int photo_people_count;

    private String photo_created_at;

    private int photo_public;

    private MultipartFile file;
}