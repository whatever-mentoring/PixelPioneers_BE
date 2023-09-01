package com.example.Posewithme.Entity;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false)
    private String photoName;
    @Column
    private String description;
    @Column(length = 500, nullable = false)
    private String photo;

    @Builder
    public Photo(int id, String photoName, String description, String photo) {
        this.id = id;
        this.photoName = photoName;
        this.description = description;
        this.photo = photo;
    }
}
