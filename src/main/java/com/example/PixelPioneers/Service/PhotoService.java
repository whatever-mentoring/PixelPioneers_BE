package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.PhotoRequest;
import com.example.PixelPioneers.DTO.PhotoResponse;

import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.repository.AlbumJPARepository;
import com.example.PixelPioneers.config.errors.exception.Exception404;
import com.example.PixelPioneers.entity.*;
import com.example.PixelPioneers.repository.PhotoJPARepository;
import com.example.PixelPioneers.repository.PoseJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class PhotoService {
    @Autowired
    private S3Uploader s3Uploader;
    private final AlbumJPARepository albumJPARepository;
    private final PhotoJPARepository photoJPARepository;
    private final PoseJPARepository poseJPARepository;


    /**
     * 아카이브
     * 사진 생성
     * 포즈 함께 생성 후 연결
     */
    public void addPhoto(int id, PhotoRequest.PhotoAddDTO requestDTO, MultipartFile file, User user) throws Exception {
        Album album = albumJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("사진첩이 존재하지 않습니다."));

        String imgurl = s3Uploader.upload(file, "photo_images");

        Photo newPhoto = Photo.builder().name(requestDTO.getName()).image(imgurl).peopleCount(requestDTO.getPeopleCount()).created_at(requestDTO.getCreated_at()).open(requestDTO.isOpen()).album(album).user(user).build();
        Photo photo = photoJPARepository.save(newPhoto);

        Pose newPose = Pose.builder().photo(photo).build();
        poseJPARepository.save(newPose);
    }

    /**
     * 아카이브
     * 사진 삭제
     * 연결된 포즈 함께 삭제
     */
    @Transactional
    public void deletePhoto(int photoId, User user) {
        Photo photo = photoJPARepository.findById(photoId)
                .orElseThrow(() -> new Exception404("사진이 존재하지 않습니다."));

        if (photo.getUser().getId() == user.getId()) {
            photoJPARepository.delete(photo);
        }
    }
}
