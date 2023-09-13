package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.User_Photo;
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

        public FindAllDTO(User_Photo userPhoto) {
            this.photo_id = userPhoto.getPhoto_id();
            this.photo_name = userPhoto.getPhoto_name();
            this.album_id = userPhoto.getAlbum().getAlbum_id();
            this.album_name = userPhoto.getAlbum().getAlbum_name();
        }
    }
}
