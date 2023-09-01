package com.example.Posewithme.Controller;

import com.example.Posewithme.DTO.PhotoResponse;
import com.example.Posewithme.Service.PhotoService;
import com.example.Posewithme.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PhotoRestController {
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
