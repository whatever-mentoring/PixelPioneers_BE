package com.example.PixelPioneers.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_photo")
public class Photo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String image;

    @Column(nullable = false)
    private int peopleCount;

    @Column(length = 255, nullable = false)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate created_at;

    @Column(nullable = false)
    private boolean open;

    @Column(nullable = false)
    private int photo_public;

    @ManyToOne
    @JoinColumn(name ="album_id")
    private Album album;

    @Builder
    public Photo(int id, String name, String image, int peopleCount, LocalDate created_at, boolean open, Album album) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.peopleCount = peopleCount;
        this.created_at = created_at;
        this.open = open;
        this.album = album;
    }
}
