package com.example.PixelPioneers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "album")
public class Album {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column
    private String image;

    @CreationTimestamp
    @Column
    private LocalDate created_at;

    @Builder
    public Album(int id, String name, String image){
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public void update(String name, String image) {
        this.name = name;
        this.image = image;
    }

}
