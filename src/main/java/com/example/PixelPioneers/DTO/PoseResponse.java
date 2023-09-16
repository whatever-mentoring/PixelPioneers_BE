package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Pose;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class PoseResponse {
    @Getter
    @Setter
    public static class PoseListDTO {
        private List<PoseDTO> poseList;

        public PoseListDTO(List<Pose> poseList) {
            this.poseList = poseList.stream()
                    .map(pose ->new PoseDTO(pose)).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class PoseDTO {
            private int id;
            private int peopleCount;
            private String imgURL;
            private int photo_id;
            private boolean open;

            public PoseDTO(Pose pose) {
                this.id = pose.getId();
                this.peopleCount = pose.getPhoto().getPeopleCount();
                this.imgURL = pose.getPhoto().getImage();
                this.photo_id = pose.getPhoto().getId();
                this.open = pose.getPhoto().isOpen();
            }
        }
    }

    @Getter
    @Setter
    public static class PoseDTO {
        private int id;
        private int peopleCount;
        private String imgURL;
        private int photo_id;

        public PoseDTO(Pose pose) {
            this.id = pose.getId();
            this.peopleCount = pose.getPhoto().getPeopleCount();
            this.imgURL = pose.getPhoto().getImage();
            this.photo_id = pose.getPhoto().getId();
        }
    }
}
