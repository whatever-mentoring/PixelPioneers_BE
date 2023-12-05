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

        Photo newPhoto = Photo.builder().name(requestDTO.getName()).image(imgurl).peopleCount(requestDTO.getPeopleCount()).created_at(requestDTO.getCreated_at()).open(requestDTO.isOpen()).pass("PENDING").album(album).user(user).build();
        Photo photo = photoJPARepository.save(newPhoto);

        Pose newPose = Pose.builder().photo(photo).build();
        poseJPARepository.save(newPose);
    }

    /**
     * 아카이브
     * 사진 조회
     */
    public PhotoResponse.PhotoDetailDTO findPhotoDetail(int photoId) {
        Photo photo = photoJPARepository.findById(photoId)
                .orElseThrow(() -> new Exception404("사진이 존재하지 않습니다."));

        return new PhotoResponse.PhotoDetailDTO(photo);
    }

    /**
     * 아카이브
     * 사진 삭제
     * 연결된 포즈 함께 삭제
     */
    @Transactional
    public void deletePhoto(int photoId, User user) throws Exception {
        Photo photo = photoJPARepository.findById(photoId)
                .orElseThrow(() -> new Exception404("사진이 존재하지 않습니다."));

        if (photo.getUser().getId() == user.getId()) {
            // S3에 저장되어 있는 사진 삭제
            String imgURL = photo.getImage();
            String key = imgURL.substring(61);
            s3Uploader.deleteFile(key);

            photoJPARepository.delete(photo);
        }
    }

    /**
     * 아카이브
     * 사진 공개범위 변경
     */
    @Transactional
    public PhotoResponse.PhotoDetailDTO updatePhotoIsOpen(int photoId, PhotoRequest.PhotoIsOpenUpdateDTO requestDTO, User user){
        Photo photo = photoJPARepository.findById(photoId)
                        .orElseThrow(() -> new Exception404("사진이 존재하지 않습니다."));

        if (user.getId() == photo.getUser().getId()) {
            photo.updateIsOpen(requestDTO.isOpen());
        }

        return new PhotoResponse.PhotoDetailDTO(photo);
    }
}