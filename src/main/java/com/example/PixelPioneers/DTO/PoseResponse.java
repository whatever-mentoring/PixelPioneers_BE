package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Pose;
import lombok.Getter;
import lombok.Setter;

public class PoseResponse {
    @Getter
    @Setter
    public static class PoseDTO {
        private int id;
        private String image;
        private int peopleCount;
        private String imgURL;
        private int photo_id;

        public PoseDTO(Pose pose) {
            this.id = pose.getId();
            this.image = pose.getPhoto().getImage();
            this.peopleCount = pose.getPhoto().getPeopleCount();
            this.photo_id = pose.getPhoto().getId();
        }
    }
}
