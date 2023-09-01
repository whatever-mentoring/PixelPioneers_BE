package com.example.Posewithme.DTO;

import com.example.Posewithme.Entity.Photo;
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
            this.photoName = photo.getPhotoName();
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
            this.photoName = photo.get().getPhotoName();
            this.description = photo.get().getDescription();
            this.photo = photo.get().getPhoto();
        }
    }
}
