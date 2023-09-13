package com.example.PixelPioneers.entity;

import lombok.*;

import javax.persistence.*;

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

    @Column(nullable = false)
    private int photo_people_count;

    @Column(length = 255, nullable = false)
    private String photo_created_at;

    @Column(nullable = false)
    private int photo_public;

    @ManyToOne
    @JoinColumn(name ="album_id")
    private Album album;

    @Builder(toBuilder = true)
    public User_Photo(int photo_id, String photo_name, String photo_image, int photo_people_count, String photo_created_at, int photo_public, Album album) {
        this.photo_id = photo_id;
        this.photo_name = photo_name;
        this.photo_image = photo_image;
        this.photo_people_count = photo_people_count;
        this.photo_created_at = photo_created_at;
        this.photo_public = photo_public;
        this.album = album;
    }
}
