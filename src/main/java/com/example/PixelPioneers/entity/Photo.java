package com.example.PixelPioneers.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
//@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "photo")
public class Photo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private int peopleCount;

    @Column(length = 255, nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created_at;

    @Column(nullable = false)
    private boolean open;

    @Column(nullable = false)
    private String pass;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @OneToOne(mappedBy = "photo")
    private Pose pose;

    @Builder
    public Photo(int id, String name, String image, int peopleCount, LocalDate created_at, boolean open, String pass, User user, Album album, Pose pose) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.peopleCount = peopleCount;
        this.created_at = created_at;
        this.open = open;
        this.pass = pass;
        this.user = user;
        this.album = album;
        this.pose = pose;
    }

//    public Photo(Photo photo){
//        this.id = photo.getId();
//        this.name = photo.getName();
//        this.image = photo.getImage();
//        this.peopleCount = photo.getPeopleCount();
//        this.created_at = photo.getCreated_at();
//        this.open = photo.isOpen();
//        this.pass = photo.getPass();
//        this.album = photo.getAlbum();
//        this.pose = photo.getPose();
//    }

    public void updateIsOpen(boolean open){
        this.open = open;
    }

    public void updateIsPass(String pass) {
        this.pass = pass;
    }
}