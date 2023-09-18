package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Album;
import io.swagger.annotations.ApiModelProperty;
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
}
