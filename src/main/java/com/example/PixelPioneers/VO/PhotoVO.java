package com.example.PixelPioneers.VO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PhotoVO {

    private String name;

    private int people_count;

    private String created_at;

    private int is_public;

    private MultipartFile file;
}