package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.repository.PhotoJPARepository;
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
    private final PhotoJPARepository photoRepository;
    @Autowired
    private S3Uploader s3Uploader;
    private final PoseService poseService;

    /**
     * 아카이브
     * 사진 전체 조회
     */
    @Transactional(readOnly = true)
    public List<PhotoResponse.FindAllDTO> findAll() {
        List<PhotoResponse.FindAllDTO> responseDTOs = photoRepository.findAll().stream()
                    .map(photo -> new PhotoResponse.FindAllDTO(photo))
                    .collect(Collectors.toList());

        return responseDTOs;
    }

    /**
     * 아카이브
     * 사진 개별 조회
     */
    @Transactional(readOnly = true)
    public PhotoResponse.FindByIdDTO findById(int photo_id) {
        Optional<Photo> photo = photoRepository.findById(photo_id);

        return new PhotoResponse.FindByIdDTO(photo);
    }

    /**
     * 아카이브
     * 사진 생성
     * 포즈 함께 생성 후 연결
     */
    @Transactional(readOnly = false)
    public PhotoResponse.FindByIdDTO create_new(Photo new_photo, MultipartFile image) throws Exception {
        Pose pose = poseService.create_new();

        String imgurl = s3Uploader.upload(image, "photo_images");

        new_photo = new_photo.toBuilder()
                .pose(pose)
                .image(imgurl)
                .build();

        photoRepository.save(new_photo);

        pose.update(new_photo.getPeopleCount(), new_photo.getImage());

        Optional<Photo> photo = photoRepository.findById(new_photo.getId());

        return new PhotoResponse.FindByIdDTO(photo);
    }

    @Transactional(readOnly = false)
    public PhotoResponse.FindByIdDTO updateById(int id){
        Photo photo = photoRepository.findById(id).get();

        photo.update((photo.getIs_public() == 1) ? 0 : 1);

        return new PhotoResponse.FindByIdDTO(Optional.of(photo));
    }

    /**
     * 아카이브
     * 사진 삭제
     * 연결된 포즈 함께 삭제
     */
    @Transactional(readOnly = false)
    public void deleteById(int id){
        int delete_pose_id = photoRepository.findById(id).get().getPose().getId();
        // 사진 삭제
        photoRepository.deleteById(id);
        // 해당 사진과 연결된 포즈 삭제
        poseService.delete(delete_pose_id);
    }

}
