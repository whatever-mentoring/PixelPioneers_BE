package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Album;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class AlbumResponse {
    @Getter
    @Setter
    public static class FindAllDTO {
        private int album_id;
        private String album_name;

        public FindAllDTO(Album album) {
            this.album_id = album.getAlbum_id();
            this.album_name = album.getAlbum_name();
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO {
        private int album_id;
        private String album_name;
        private String album_created_at;

        public FindByIdDTO(Optional<Album> album) {
            this.album_id = album.get().getAlbum_id();
            this.album_name = album.get().getAlbum_name();
//            this.album_created_at = album.get().getAlbum_created_at();
        }
    }
}
