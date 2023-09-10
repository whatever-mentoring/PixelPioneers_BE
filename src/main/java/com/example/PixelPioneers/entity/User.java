package com.example.PixelPioneers.entity;

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
@Table(name = "user")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int user_id;

    @Column(length = 500, nullable = false)
    private String user_email;

    @Column(length = 255, nullable = false)
    private String user_password;

    @Column(length = 255, nullable = false)
    private String user_nickname;

    @Column(length = 500, nullable = false)
    private String user_image;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<User_Album> user_albums = new ArrayList<>();

    @Builder
    public User(int user_id, String user_email, String user_password, String user_nickname, String user_image){
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_nickname = user_nickname;
        this.user_image = user_image;
    }
}
