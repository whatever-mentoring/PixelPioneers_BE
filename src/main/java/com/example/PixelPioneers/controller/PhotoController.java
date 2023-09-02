package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.Service.PhotoService;
import com.example.PixelPioneers.utils.ApiUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/photos")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<PhotoResponse.FindAllDTO> responseDTOs = photoService.findAll(page);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        PhotoResponse.FindByIdDTO responseDTO = photoService.findById(id);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
