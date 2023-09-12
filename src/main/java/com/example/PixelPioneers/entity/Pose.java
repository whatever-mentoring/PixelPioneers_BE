package com.example.PixelPioneers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "pose")
public class Pose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pose_id;

    //user_photo와 1대 1 연결.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private User_Photo userPhoto;


    @Builder(toBuilder = true)
    public Pose(int pose_id, User_Photo userPhoto) {
        this.pose_id = pose_id;
        this.userPhoto = userPhoto;
    }
}
