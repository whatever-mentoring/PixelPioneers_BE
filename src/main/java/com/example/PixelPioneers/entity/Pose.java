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
    private int id;

    @Column(name = "people_count")
    private int peopleCount;

    @Column(length = 500)
    private String imgURL;

    @OneToOne(mappedBy = "pose")
    private Photo photo;

    @Builder(toBuilder = true)
    public Pose(int id, int peopleCount, String imgURL, Photo photo) {
        this.id = id;
        this.peopleCount = peopleCount;
        this.imgURL = imgURL;
        this.photo = photo;
    }

    public void update(int peopleCount, String imgURL){
        this.peopleCount = peopleCount;
        this.imgURL = imgURL;
    }
}
