package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.AlbumRequest;
import com.example.PixelPioneers.DTO.AlbumResponse;
import com.example.PixelPioneers.DTO.PhotoRequest;
import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.config.errors.exception.Exception404;
import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.entity.User;
import com.example.PixelPioneers.entity.User_Album;
import com.example.PixelPioneers.repository.AlbumJPARepository;
import com.example.PixelPioneers.repository.PhotoJPARepository;
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
public class PhotoService {
    private final PhotoJPARepository photoJPARepository;
    private final AlbumJPARepository albumJPARepository;

    public void addPhoto(int id, PhotoRequest.PhotoAddDTO requestDTO) {
        Album album = albumJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("사진첩이 존재하지 않습니다."));

        Photo newPhoto = Photo.builder().name(requestDTO.getName()).image(requestDTO.getImage()).peopleCount(requestDTO.getPeopleCount()).created_at(requestDTO.getCreated_at()).open(requestDTO.isOpen()).album(album).build();
        photoJPARepository.save(newPhoto);
    }

//    public void addPhoto(AlbumRequest.AlbumAddDTO requestDTO, User sessionUser) {
//        String name = requestDTO.getName();
//        String image = requestDTO.getImage();
//        Album newAlbum = Album.builder().name(name).image(image).build();
//        Album album = albumJPARepository.save(newAlbum);
//
//        List<Integer> userIdList = requestDTO.getUserIdList();
//        for (Integer userId: userIdList) {
//            User user = userJPARepository.findById(userId)
//                    .orElseThrow(() -> new Exception404("사용자가 존재하지 않습니다."));
//            User_Album newUser_Album = User_Album.builder().user(user).album(album).build();
//            user_albumJPARepository.save(newUser_Album);
//        }
//    }


//    public List<PhotoResponse.FindAllDTO> findAll(int page) {
//        Pageable pageable = PageRequest.of(page, 15);
//
//        Page<Photo> pageContent = photoRepository.findAll(pageable);
//
//        List<PhotoResponse.FindAllDTO> responseDTOs = pageContent.getContent().stream()
//                .map(photo -> new PhotoResponse.FindAllDTO(photo))
//                .collect(Collectors.toList());
//        return responseDTOs;
//    }
//
//    public PhotoResponse.FindByIdDTO findById(int photo_id) {
//        Optional<Photo> photo = photoRepository.findById(photo_id);
//        return new PhotoResponse.FindByIdDTO(photo);
//    }

    //public PhotoResponse.FindByIdDTO create(Photo new_photo){
    //    photoRepository.save(new_photo);
    //    Optional<Photo> photo = photoRepository.findById(new_photo.getPhoto_id());
    //    return new PhotoResponse.FindByIdDTO(photo);
    //}

//    public PhotoResponse.FindByIdDTO create_new(Photo new_photo, int album_id){
//        new_photo = new_photo.toBuilder()
//                .album(albumJPARepository.findById(album_id).get())
//                .build();
//
//        photoRepository.save(new_photo);
//
//        Optional<Photo> photo = photoRepository.findById(new_photo.getPhoto_id());
//        return new PhotoResponse.FindByIdDTO(photo);
//    }


}
