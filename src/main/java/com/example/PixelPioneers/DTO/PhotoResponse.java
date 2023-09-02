package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Photo;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class PhotoResponse {
    @Getter
    @Setter
    public static class FindAllDTO {
        private int photo_id;
        private String photo_image;

        public FindAllDTO(Photo photo) {
            this.photo_id = photo.getPhoto_id();
            this.photo_image = photo.getPhoto_image();
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO {
        private int photo_id;
        private String photo_name;
        private String photo_image;
        private int photo_people_count;
        private String photo_created_at;

        public FindByIdDTO(Optional<Photo> photo) {
            this.photo_id = photo.get().getPhoto_id();
            this.photo_name = photo.get().getPhoto_name();
            this.photo_image = photo.get().getPhoto_image();
            this.photo_people_count = photo.get().getPhoto_people_count();
            this.photo_created_at = photo.get().getPhoto_created_at();
        }
    }
}
