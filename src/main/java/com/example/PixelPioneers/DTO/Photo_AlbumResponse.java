package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Photo;
import lombok.Getter;
import lombok.Setter;

public class Photo_AlbumResponse {
    @Getter
    @Setter
    public static class FindAllDTO {
        private int photo_id;
        private String photo_name;
        private int album_id;
        private String album_name;

        public FindAllDTO(Photo userPhoto) {
            this.photo_id = userPhoto.getId();
            this.photo_name = userPhoto.getName();
            this.album_id = userPhoto.getAlbum().getAlbum_id();
            this.album_name = userPhoto.getAlbum().getAlbum_name();
        }
    }
}
