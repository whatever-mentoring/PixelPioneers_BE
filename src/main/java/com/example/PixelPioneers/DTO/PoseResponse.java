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
            private int pose_id;
            private int photo_id;
            private String photo_imageURL;

            public PoseDTO(Pose pose) {
                this.pose_id = pose.getPose_id();
                this.photo_id = pose.getUserPhoto().getPhoto_id();
                this.photo_imageURL = pose.getUserPhoto().getPhoto_image();
            }
        }
    }

    @Getter
    @Setter
    public static class PoseDetailDTO {
        private int pose_id;
        private int photo_id;
        private String photo_imageURL;
        private int photo_public;
        private String photo_name;
        private String photo_created_at;
        private int photo_people_count;
        private int album_id;
        private String album_name;


        public PoseDetailDTO(Pose pose) {
            this.pose_id = pose.getPose_id();
            this.photo_public = pose.getUserPhoto().getPhoto_public();
            this.photo_imageURL = pose.getUserPhoto().getPhoto_image();
            this.photo_name = pose.getUserPhoto().getPhoto_name();
            this.photo_created_at = pose.getUserPhoto().getPhoto_created_at();
            this.photo_people_count = pose.getUserPhoto().getPhoto_people_count();
            this.album_id = pose.getUserPhoto().getAlbum().getAlbum_id();
            this.album_name = pose.getUserPhoto().getAlbum().getAlbum_name();
        }
    }
}
