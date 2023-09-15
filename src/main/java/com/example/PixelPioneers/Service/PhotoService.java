package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.PhotoResponse;
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

    @Transactional(readOnly = true)
    public List<PhotoResponse.FindAllDTO> findAll() {
        List<PhotoResponse.FindAllDTO> responseDTOs = photoRepository.findAll().stream()
                    .map(photo -> new PhotoResponse.FindAllDTO(photo))
                    .collect(Collectors.toList());

        return responseDTOs;
    }

    @Transactional(readOnly = true)
    public PhotoResponse.FindByIdDTO findById(int photo_id) {
        Optional<Photo> photo = photoRepository.findById(photo_id);

        return new PhotoResponse.FindByIdDTO(photo);
    }

    //public PhotoResponse.FindByIdDTO create(Photo new_photo){
    //    photoRepository.save(new_photo);
    //    Optional<Photo> photo = photoRepository.findById(new_photo.getPhoto_id());
    //    return new PhotoResponse.FindByIdDTO(photo);
    //}

    @Transactional(readOnly = false)
    public PhotoResponse.FindByIdDTO create_new(Photo new__photo, MultipartFile image) throws Exception {

        String imgurl = s3Uploader.upload(image, "user_photo_images");

        new__photo = new__photo.toBuilder()
                .photo_image(imgurl)
                .build();

        photoRepository.save(new__photo);

        Optional<Photo> photo = photoRepository.findById(new__photo.getPhoto_id());

        return new PhotoResponse.FindByIdDTO(photo);
    }


}
