package com.example.PixelPioneers.entity;

import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(length = 255, nullable = false)
    private String album_name;

    @Column(length = 255, nullable = false)
    private String album_created_at;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<User_Photo> user_photos = new ArrayList<>();

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<User_Album> user_albums = new ArrayList<>();

    @Builder
    public Album(int album_id, String album_name, String album_created_at){
        this.album_id = album_id;
        this.album_name = album_name;
        this.album_created_at = album_created_at;
    }
}
