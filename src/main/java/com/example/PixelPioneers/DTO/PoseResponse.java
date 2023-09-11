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
            private String image;
            private int peopleCount;

            public PoseDTO(Pose pose) {
                this.id = pose.getId();
                this.image = pose.getImage();
                this.peopleCount = pose.getPeopleCount();
            }
        }
    }

    @Getter
    @Setter
    public static class PoseDetailDTO {
        private int id;
        private String image;
        private String hashtag;
        private int peopleCount;

        public PoseDetailDTO(Pose pose) {
            this.id = pose.getId();
            this.image = pose.getImage();
            this.hashtag = pose.getHashtag();
            this.peopleCount = pose.getPeopleCount();
        }
    }
}
