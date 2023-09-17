package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Photo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

public class PhotoResponse {
    @Getter
    @Setter
    public static class PhotoListDTO {
        private int id;
        private String name;
        private String image;
        private LocalDate created_at;

        public PhotoListDTO(Photo photo) {
            this.id = photo.getId();
            this.name = photo.getName();
            this.image = photo.getImage();
            this.created_at = photo.getCreated_at();
        }
    }
}
