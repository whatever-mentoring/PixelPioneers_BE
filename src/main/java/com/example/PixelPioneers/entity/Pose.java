package com.example.PixelPioneers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "pose")
public class Pose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "photo_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Photo photo;

    @Builder
    public Pose(int id, Photo photo) {
        this.id = id;
        this.photo = photo;
    }
}
