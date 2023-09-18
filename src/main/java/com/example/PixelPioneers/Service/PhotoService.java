package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.PhotoRequest;
import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.repository.AlbumJPARepository;
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
    private final AlbumJPARepository albumJPARepository;

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
    public PhotoResponse.FindByIdDTO create_new(PhotoRequest photoRequest, int album_id) throws Exception {
        String imgurl = s3Uploader.upload(photoRequest.getFile(), "photo_images");

        Photo new_photo = Photo.builder().name(photoRequest.getName())
                .peopleCount(photoRequest.getPeopleCount())
                .created_at(photoRequest.getCreated_at())
                .open(photoRequest.isOpen())
                .image(imgurl)
                .album(albumJPARepository.findById(album_id).get())
                .build();

        photoRepository.save(new_photo);

        Pose pose = poseService.create_new(new_photo);

        Optional<Photo> photo = photoRepository.findById(new_photo.getId());

        return new PhotoResponse.FindByIdDTO(photo);
    }

    @Transactional(readOnly = false)
    public PhotoResponse.FindByIdDTO updateById(int id){
        Photo photo = photoRepository.findById(id).get();

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
        int delete_pose_id = photoRepository.findById(id).get().getPose().getId();
        // 해당 사진과 연결된 포즈 삭제
        poseService.delete(delete_pose_id);
        // 사진 삭제
        photoRepository.deleteById(id);

    }

}
