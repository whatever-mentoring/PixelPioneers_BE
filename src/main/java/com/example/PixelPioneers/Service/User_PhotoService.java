package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.User_PhotoResponse;
import com.example.PixelPioneers.entity.User_Photo;
import com.example.PixelPioneers.repository.AlbumJPARepository;
import com.example.PixelPioneers.repository.User_PhotoJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class User_PhotoService {
    private final User_PhotoJPARepository photoRepository;
    private final AlbumJPARepository albumJPARepository;

    public List<User_PhotoResponse.FindAllDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 15);

        Page<User_Photo> pageContent = photoRepository.findAll(pageable);

        List<User_PhotoResponse.FindAllDTO> responseDTOs = pageContent.getContent().stream()
                .map(photo -> new User_PhotoResponse.FindAllDTO(photo))
                .collect(Collectors.toList());
        return responseDTOs;
    }

    public User_PhotoResponse.FindByIdDTO findById(int photo_id) {
        Optional<User_Photo> photo = photoRepository.findById(photo_id);
        return new User_PhotoResponse.FindByIdDTO(photo);
    }

    //public PhotoResponse.FindByIdDTO create(Photo new_photo){
    //    photoRepository.save(new_photo);
    //    Optional<Photo> photo = photoRepository.findById(new_photo.getPhoto_id());
    //    return new PhotoResponse.FindByIdDTO(photo);
    //}

    public User_PhotoResponse.FindByIdDTO create_new(User_Photo new_User_photo, int album_id){
        new_User_photo = new_User_photo.toBuilder()
                .album(albumJPARepository.findById(album_id).get())
                .build();

        photoRepository.save(new_User_photo);

        Optional<User_Photo> photo = photoRepository.findById(new_User_photo.getPhoto_id());
        return new User_PhotoResponse.FindByIdDTO(photo);
    }


}
