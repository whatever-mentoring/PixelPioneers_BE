package com.example.PixelPioneers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "User_Album")
public class User_Album {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int user_album_id;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name ="album_id")
    private Album album;

    @Builder
    public User_Album(int user_album_id, User user, Album album) {
        this.user_album_id = user_album_id;
        this.user = user;
        this.album = album;
    }
}
