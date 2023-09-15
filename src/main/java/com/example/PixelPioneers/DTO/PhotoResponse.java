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

        private int album_id;
        private String album_name;

        public FindAllDTO(Photo userPhoto) {
            this.photo_id = userPhoto.getPhoto_id();
            this.photo_image = userPhoto.getPhoto_image();

            this.album_id = userPhoto.getAlbum().getAlbum_id();
            this.album_name = userPhoto.getAlbum().getAlbum_name();
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
        private int photo_public;
        private int album_id;
        private String album_name;

        public FindByIdDTO(Optional<Photo> photo) {
            this.photo_id = photo.get().getPhoto_id();
            this.photo_name = photo.get().getPhoto_name();
            this.photo_image = photo.get().getPhoto_image();
            this.photo_people_count = photo.get().getPhoto_people_count();
            this.photo_created_at = photo.get().getPhoto_created_at();
            this.photo_public = photo.get().getPhoto_public();
            this.album_id = photo.get().getAlbum().getAlbum_id();
            this.album_name = photo.get().getAlbum().getAlbum_name();
        }
    }
}
