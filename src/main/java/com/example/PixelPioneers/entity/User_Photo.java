package com.example.PixelPioneers.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_photo")
public class User_Photo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int photo_id;

    @Column(length = 500, nullable = false)
    private String photo_name;

    @Column(length = 500)
    private String photo_image;

    @Column(name = "photo_people_count", nullable = false)
    private int peopleCount;

    @Column(length = 255, nullable = false)
    private String photo_created_at;

    @Column(nullable = false)
    private int photo_public;

    @ManyToOne
    @JoinColumn(name ="album_id")
    private Album album;

    //포즈와 1대 1 연결.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pose_id")
    private Pose pose;

    @Builder(toBuilder = true)
    public User_Photo(int photo_id, String photo_name, String photo_image, int peopleCount, String photo_created_at, int photo_public, Album album, Pose pose) {
        this.photo_id = photo_id;
        this.photo_name = photo_name;
        this.photo_image = photo_image;
        this.peopleCount = peopleCount;
        this.photo_created_at = photo_created_at;
        this.photo_public = photo_public;
        this.album = album;
        this.pose = pose;
    }
}
