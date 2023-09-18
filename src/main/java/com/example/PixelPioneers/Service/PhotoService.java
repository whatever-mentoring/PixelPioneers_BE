package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.PhotoRequest;
import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.repository.AlbumJPARepository;
import com.example.PixelPioneers.config.errors.exception.Exception404;
import com.example.PixelPioneers.entity.*;
import com.example.PixelPioneers.repository.AlbumJPARepository;
import com.example.PixelPioneers.repository.PhotoJPARepository;
import com.example.PixelPioneers.repository.PoseJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PhotoService {
    @Autowired
    private S3Uploader s3Uploader;
    private final PoseService poseService;
    private final AlbumJPARepository albumJPARepository;
    private final PhotoJPARepository photoJPARepository;
    private final PoseJPARepository poseJPARepository;

    /**
     * 아카이브
     * 사진 생성
     * 포즈 함께 생성 후 연결
     */
    public void addPhoto(int id, PhotoRequest.PhotoAddDTO requestDTO, MultipartFile file) throws Exception {
        Album album = albumJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("사진첩이 존재하지 않습니다."));

        String imgurl = s3Uploader.upload(file, "photo_images");

        Photo newPhoto = Photo.builder().name(requestDTO.getName()).image(imgurl).peopleCount(requestDTO.getPeopleCount()).created_at(requestDTO.getCreated_at()).open(requestDTO.isOpen()).album(album).build();
        Photo photo = photoJPARepository.save(newPhoto);

        Pose newPose = Pose.builder().photo(photo).build();
        poseJPARepository.save(newPose);
    }

    /**
     * 아카이브
     * 사진 공개범위 변경
     */
    @Transactional(readOnly = false)
    public PhotoResponse.FindByIdDTO updateById(int id){
        Photo photo = photoJPARepository.findById(id).get();

        photo.updateopen((photo.isOpen() == true) ? false : true);

        return new PhotoResponse.FindByIdDTO(Optional.of(photo));
    }

    /**
     * 아카이브
     * 사진 삭제
     * 연결된 포즈 함께 삭제
     */
    @Transactional(readOnly = false)
    public void deleteById(int id){
        int delete_pose_id = photoJPARepository.findById(id).get().getPose().getId();
        // 해당 사진과 연결된 포즈 삭제
        poseJPARepository.deleteById(delete_pose_id);
        // 사진 삭제
        photoJPARepository.deleteById(id);
    }
}
