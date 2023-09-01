package com.example.PixelPioneers.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "poses")
public class pose {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String img_path;
}

