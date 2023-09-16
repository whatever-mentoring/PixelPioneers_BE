package com.example.PixelPioneers.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class PhotoRequest {

    private String name;

    private int peopleCount;

    private LocalDate created_at;

    private boolean open;

    private MultipartFile file;
}
