package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.User_PhotoResponse;
import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.entity.User_Photo;
import com.example.PixelPioneers.repository.User_PhotoJPARepository;
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
public class User_PhotoService {
    private final User_PhotoJPARepository photoRepository;
    @Autowired
    private S3Uploader s3Uploader;
    private final PoseService poseService;

    /**
     * 아카이브
     * 사진 전체 조회
     */
    @Transactional(readOnly = true)
    public List<User_PhotoResponse.FindAllDTO> findAll() {
        List<User_PhotoResponse.FindAllDTO> responseDTOs = photoRepository.findAll().stream()
                    .map(photo -> new User_PhotoResponse.FindAllDTO(photo))
                    .collect(Collectors.toList());

        return responseDTOs;
    }

    /**
     * 아카이브
     * 사진 개별 조회
     */
    @Transactional(readOnly = true)
    public User_PhotoResponse.FindByIdDTO findById(int photo_id) {
        Optional<User_Photo> photo = photoRepository.findById(photo_id);

        return new User_PhotoResponse.FindByIdDTO(photo);
    }

    /**
     * 아카이브
     * 사진 생성
     * 포즈 함께 생성 후 연결
     */
    @Transactional(readOnly = false)
    public User_PhotoResponse.FindByIdDTO create_new(User_Photo new_user_photo, MultipartFile image) throws Exception {
        // #category 유저 사진: 1, 관리자 사진: 0
        Pose pose = poseService.create_new(1);

        String imgurl = s3Uploader.upload(image, "user_photo_images");

        new_user_photo = new_user_photo.toBuilder()
                .pose(pose)
                .photo_image(imgurl)
                .build();

        photoRepository.save(new_user_photo);

        Optional<User_Photo> photo = photoRepository.findById(new_user_photo.getPhoto_id());

        return new User_PhotoResponse.FindByIdDTO(photo);
    }

    /**
     * 아카이브
     * 사진 삭제
     * 연결된 포즈 함께 삭제
     */
    @Transactional(readOnly = false)
    public void deleteById(int photo_id){
        int delete_pose_id = photoRepository.findById(photo_id).get().getPose().getPose_id();
        // 사진 삭제
        photoRepository.deleteById(photo_id);
        // 해당 사진과 연결된 포즈 삭제
        poseService.delete(delete_pose_id);
    }

}
