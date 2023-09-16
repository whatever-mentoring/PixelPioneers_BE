package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Photo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
            this.album_id = photo.getAlbum().getId();
            this.album_name = photo.getAlbum().getName();
        }
    }

    @Getter
    @Setter
    public static class PhotoListDTO {
        private int id;
        private String name;
        private String image;
        private LocalDate created_at;

        public PhotoListDTO(Photo photo) {
            this.id = photo.getId();
            this.name = photo.getName();
            this.image = photo.getImage();
            this.created_at = photo.getCreated_at();
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO {
        private int id;
        private String name;
        private String image;
        private int peopleCount;
        private String created_at;
        private boolean open;
        private int album_id;
        private String album_name;
        private int pose_id;

        public FindByIdDTO(Optional<Photo> photo) {
            this.id = photo.get().getId();
            this.name = photo.get().getName();
            this.image = photo.get().getImage();
            this.peopleCount = photo.get().getPeopleCount();
            this.open = photo.get().isOpen();
            this.album_id = photo.get().getAlbum().getId();
            this.album_name = photo.get().getAlbum().getName();
            this.pose_id = photo.get().getPose().getId();
        }
    }
}
