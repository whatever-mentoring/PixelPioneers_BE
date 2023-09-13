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
        private String image;
        private int album_id;
        private String album_name;

        public FindAllDTO(Photo photo) {
            this.id = photo.getId();
            this.image = photo.getImage();
            this.album_id = photo.getAlbum().getAlbum_id();
            this.album_name = photo.getAlbum().getAlbum_name();
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO {
        private int id;
        private String name;
        private String image;
        private int people_count;
        private String created_at;
        private int is_public;
        private int album_id;
        private String album_name;
        private int pose_id;

        public FindByIdDTO(Optional<Photo> photo) {
            this.id = photo.get().getId();
            this.name = photo.get().getName();
            this.image = photo.get().getImage();
            this.people_count = photo.get().getPeopleCount();
            this.created_at = photo.get().getCreated_at();
            this.is_public = photo.get().getIs_public();
            this.album_id = photo.get().getAlbum().getAlbum_id();
            this.album_name = photo.get().getAlbum().getAlbum_name();
            this.pose_id = photo.get().getPose().getId();
        }
    }
}
