package com.example.PixelPioneers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "poses")
public class pose {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(length = 100, nullable = false)
    private String poseName;

    @Column
    private String description;

    @Column(length = 500, nullable = false)
    private String pose;

    @Builder
    public pose(int id, String poseName, String description, String pose) {
        this.id = id;
        this.poseName = poseName;
        this.description = description;
        this.pose = pose;
    }
}

