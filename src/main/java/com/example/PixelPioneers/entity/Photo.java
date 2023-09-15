package com.example.PixelPioneers.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;

@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "photo")
public class Photo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 500)
    private String image;

    @Column(name = "people_count", nullable = false)
    private int peopleCount;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String created_at;

    @Column(nullable = false)
    private int is_public;

    @ManyToOne
    @JoinColumn(name ="album_id")
    private Album album;

    //포즈와 1대 1 연결.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pose_id")
    private Pose pose;

    @Builder(toBuilder = true)
    public Photo(int id, String name, String image, int peopleCount, String created_at, int is_public, Album album, Pose pose) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.peopleCount = peopleCount;
        this.created_at = created_at;
        this.is_public = is_public;
        this.album = album;
        this.pose = pose;
    }

    public void update(int is_public){
        this.is_public = is_public;
    }
}
