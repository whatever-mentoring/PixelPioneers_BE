package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Album;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class AlbumResponse {
    @Getter
    @Setter
    public static class AlbumListDTO {
        private int id;
        private String name;
        private String image;

        public AlbumListDTO(Album album) {
            this.id = album.getId();
            this.name = album.getName();
        }
    }

//    @Getter
//    @Setter
//    public static class AlbumDetailDTO {
//        private int id;
//        private String name;
//        private String image;
//
//        public AlbumDetailDTO(Optional<Album> album) {
//            this.id = album.get().getId();
//            this.name = album.get().name();
////            this.album_created_at = album.get().getAlbum_created_at();
//        }
//    }
}
