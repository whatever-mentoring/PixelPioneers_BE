package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.User_PhotoResponse;
import com.example.PixelPioneers.entity.User_Photo;
import com.example.PixelPioneers.repository.User_PhotoJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public List<User_PhotoResponse.FindAllDTO> findAll() {
        List<User_PhotoResponse.FindAllDTO> responseDTOs = photoRepository.findAll().stream()
                    .map(photo -> new User_PhotoResponse.FindAllDTO(photo))
                    .collect(Collectors.toList());

        return responseDTOs;
    }

    @Transactional(readOnly = true)
    public User_PhotoResponse.FindByIdDTO findById(int photo_id) {
        Optional<User_Photo> photo = photoRepository.findById(photo_id);

        return new User_PhotoResponse.FindByIdDTO(photo);
    }

    //public PhotoResponse.FindByIdDTO create(Photo new_photo){
    //    photoRepository.save(new_photo);
    //    Optional<Photo> photo = photoRepository.findById(new_photo.getPhoto_id());
    //    return new PhotoResponse.FindByIdDTO(photo);
    //}

    @Transactional(readOnly = false)
    public User_PhotoResponse.FindByIdDTO create_new(User_Photo new_user_photo, MultipartFile image) throws Exception {

        String imgurl = s3Uploader.upload(image, "user_photo_images");

        new_user_photo = new_user_photo.toBuilder()
                .photo_image(imgurl)
                .build();

        photoRepository.save(new_user_photo);

        Optional<User_Photo> photo = photoRepository.findById(new_user_photo.getPhoto_id());

        return new User_PhotoResponse.FindByIdDTO(photo);
    }


}
