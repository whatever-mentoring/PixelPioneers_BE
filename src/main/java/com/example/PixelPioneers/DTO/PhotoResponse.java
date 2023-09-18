package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Photo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PhotoResponse {

    @Getter
    @Setter
    public static class PhotoListDTO {
        private List<PhotoResponse.PhotoListDTO.PhotoDTO> photoList;

        public PhotoListDTO(List<Photo> photoList) {
            this.photoList = photoList.stream()
                    .map(photo ->new PhotoResponse.PhotoListDTO.PhotoDTO(photo)).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public static class PhotoDTO {
            private int id;
            private String name;
            private String image;
            private int peopleCount;
            private int pose_id;

            public PhotoDTO(Photo photo) {
                this.id = photo.getId();
                this.name = photo.getName();
                this.peopleCount = photo.getPeopleCount();
                this.image = photo.getImage();
                this.pose_id = photo.getPose().getId();
            }
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
