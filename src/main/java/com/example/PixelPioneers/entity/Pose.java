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

    @Column(nullable = false)
    private int pose_category;

    @OneToOne(mappedBy = "pose")
    private User_Photo userPhoto;

    @Builder(toBuilder = true)
    public Pose(int pose_id, int pose_category, User_Photo userPhoto) {
        this.pose_id = pose_id;
        this.pose_category = pose_category;
        this.userPhoto = userPhoto;
    }
}
