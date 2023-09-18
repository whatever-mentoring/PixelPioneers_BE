package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.entity.Pose;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class PoseResponse {
    @Getter
    @Setter
    public static class PoseDTO {
        private int id;
        private String image;
        private int peopleCount;

        public PoseDTO(Pose pose) {
            this.id = pose.getId();
            this.image = pose.getPhoto().getImage();
            this.peopleCount = pose.getPhoto().getPeopleCount();
        }
    }
}
