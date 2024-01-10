package com.example.PixelPioneers.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private int id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 256, nullable = false)
    private String password;

    @Column(length = 50, nullable = false, unique = true)
    private String nickname;

    @Column
    private String image;

    @Column
    private String age_range;

    @Column
    private String gender;

    @Column
    private String role;

    @Builder
    public User(int id, String email, String password, String nickname, String image, String role, String age_range, String gender) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.image = image;
        this.age_range = age_range;
        this.gender = gender;
        this.role = role;
    }

    public void updateUserProfile(String nickname, String image) {
        this.nickname = nickname;
        this.image = image;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateAgeRange(String ageRange) {
        this.age_range = ageRange;
    }

    public void updateGender(String gender) {
        this.gender = gender;
    }
}