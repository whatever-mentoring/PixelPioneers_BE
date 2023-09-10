package com.example.Posewithme.entity;

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
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "hashtag")
    private String hashtag;
    @Column(name = "people_count", nullable = false)
    private int peopleCount;

    @Builder
    public Pose(int id, String image, String hashtag, int peopleCount) {
        this.id = id;
        this.image = image;
        this.hashtag = hashtag;
        this.peopleCount = peopleCount;
    }
}
