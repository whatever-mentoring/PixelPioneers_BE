package com.example.PixelPioneers.response;

import com.example.PixelPioneers.entity.pose;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class PoseResponse {
    @Getter
    @Setter
    public static class FindAllDTO {
        private int id;
        private String photoName;
        private String photo;

        public FindAllDTO(pose photo) {
            this.id = photo.getId();
            this.photoName = photo.getPoseName();
            this.photo = photo.getPose();
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO {
        private int id;
        private String photoName;
        private String description;
        private String photo;

        public FindByIdDTO(Optional<pose> photo) {
            this.id = photo.get().getId();
            this.photoName = photo.get().getPoseName();
            this.description = photo.get().getDescription();
            this.photo = photo.get().getPose();
        }
    }
}