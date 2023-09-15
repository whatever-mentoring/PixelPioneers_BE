package com.example.PixelPioneers.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PhotoRequest {

    private String name;

    private int people_count;

    private String created_at;

    private int is_public;

    private MultipartFile file;
}