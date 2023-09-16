package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Album;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class AlbumResponse {
    @Getter
    @Setter
    public static class AlbumDTO {
        private int id;
        private String name;
        private String image;

        public AlbumDTO(Album album) {
            this.id = album.getId();
            this.name = album.getName();
            this.image = album.getImage();
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
