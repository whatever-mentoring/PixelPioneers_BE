package com.example.PixelPioneers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 256, nullable = false)
    private String password;

    @Column(length = 16, nullable = false, unique = true)
    private String user_nickname;

    @Column
    private String user_image;

    @Column
    private String user_role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<User_Album> user_albums = new ArrayList<>();

    @Builder
    public User(int user_id, String email, String password, String user_nickname, String user_image, String user_role){
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.user_nickname = user_nickname;
        this.user_image = user_image;
        this.user_role = user_role;
    }
}
