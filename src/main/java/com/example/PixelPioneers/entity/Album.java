package com.example.PixelPioneers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
    private int album_id;

    @Column(length = 10, nullable = false)
    private String album_name;

    @Column
    private String album_image;

    @CreationTimestamp
    @Column
    private LocalDateTime album_created_at;

//    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
//    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<User_Album> user_albums = new ArrayList<>();

    @Builder
    public Album(int album_id, String album_name, String album_image){
        this.album_id = album_id;
        this.album_name = album_name;
        this.album_image = album_image;
    }
}
