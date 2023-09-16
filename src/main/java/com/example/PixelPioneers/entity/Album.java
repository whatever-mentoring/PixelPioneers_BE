package com.example.PixelPioneers.entity;

import io.swagger.annotations.ApiParam;
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
    private int id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column
    private String image;

    @CreationTimestamp
    @Column
    private LocalDateTime created_at;

//    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
//    private List<User_Album> user_albumList = new ArrayList<>();

//    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
//    private List<Photo> photos = new ArrayList<>();

    @Builder
    public Album(int id, String name, String image){
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
