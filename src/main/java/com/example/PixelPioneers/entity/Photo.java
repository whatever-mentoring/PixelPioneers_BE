package com.example.PixelPioneers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "photo")
public class Photo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(length = 100, nullable = false)
    private String photoname;

    @Column
    private String description;

    @Column(length = 500, nullable = false)
    private String photo;

    @Builder
    public Photo(int id, String photoname, String description, String photo) {
        this.id = id;
        this.photoname = photoname;
        this.description = description;
        this.photo = photo;
    }
}
