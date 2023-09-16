package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.User_Album;
import lombok.Getter;
import lombok.Setter;

public class User_AlbumResponse {
    @Getter
    @Setter
    public static class FindAllDTO {
        private int user_album_id;
        private int album_id;
        private int user_id;

        public FindAllDTO(User_Album userAlbum) {
            this.user_album_id = userAlbum.getId();
            this.album_id = userAlbum.getAlbum().getId();
            this.user_id = userAlbum.getUser().getId();
        }
    }
}