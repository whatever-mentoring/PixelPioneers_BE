package com.example.PixelPioneers.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PhotoRequest {

    private String name;

    private int peopleCount;

    private String created_at;

    private boolean open;

    private MultipartFile file;
}
