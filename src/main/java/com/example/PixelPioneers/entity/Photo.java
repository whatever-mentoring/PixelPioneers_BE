package com.example.PixelPioneers.entity;

import lombok.*;
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

    @Column(name = "people_count", nullable = false)
    private int peopleCount;

    @Column(length = 255, nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created_at;

    @Column(nullable = false)
    private boolean open;

    @ManyToOne
    @JoinColumn(name ="album_id")
    private Album album;

    @OneToOne(mappedBy = "photo")
    private Pose pose;

    @Builder(toBuilder = true)
    public Photo(int id, String name, String image, int peopleCount, LocalDate created_at, boolean open, Album album, Pose pose) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.peopleCount = peopleCount;
        this.created_at = created_at;
        this.open = open;
        this.album = album;
        this.pose = pose;
    }

    public Photo(Photo photo){
        this.id = photo.getId();
        this.name = photo.getName();
        this.image = photo.getImage();
        this.peopleCount = photo.getPeopleCount();
        this.created_at = photo.getCreated_at();
        this.open = photo.isOpen();
        this.album = photo.getAlbum();
        this.pose = photo.getPose();
    }

    public void updateopen(boolean open){
        this.open = open;
    }

    public void updatepose(Pose pose){
        this.pose = pose;
    }
}
