package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Photo;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class PhotoResponse {
    @Getter
    @Setter
    public static class FindAllDTO {
        private int id;
        private String photoName;
        private String photo;

        public FindAllDTO(Photo photo) {
            this.id = photo.getId();
            this.photoName = photo.getPhotoname();
            this.photo = photo.getPhoto();
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO {
        private int id;
        private String photoName;
        private String description;
        private String photo;

        public FindByIdDTO(Optional<Photo> photo) {
            this.id = photo.get().getId();
            this.photoName = photo.get().getPhotoname();
            this.description = photo.get().getDescription();
            this.photo = photo.get().getPhoto();
        }
    }
}
