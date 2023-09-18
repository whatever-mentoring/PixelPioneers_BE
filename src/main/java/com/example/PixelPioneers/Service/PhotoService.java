package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.AlbumRequest;
import com.example.PixelPioneers.DTO.AlbumResponse;
import com.example.PixelPioneers.DTO.PhotoRequest;
import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.config.errors.exception.Exception404;
import com.example.PixelPioneers.entity.*;
import com.example.PixelPioneers.repository.AlbumJPARepository;
import com.example.PixelPioneers.repository.PhotoJPARepository;
import com.example.PixelPioneers.repository.PoseJPARepository;
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
    private final PoseJPARepository poseJPARepository;

    public void addPhoto(int id, PhotoRequest.PhotoAddDTO requestDTO, User user) {
        Album album = albumJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("사진첩이 존재하지 않습니다."));

        Photo newPhoto = Photo.builder().name(requestDTO.getName()).image(requestDTO.getImage()).peopleCount(requestDTO.getPeopleCount()).created_at(requestDTO.getCreated_at()).open(requestDTO.isOpen()).user(user).album(album).build();
        Photo photo = photoJPARepository.save(newPhoto);

        Pose newPose = Pose.builder().photo(photo).build();
        poseJPARepository.save(newPose);
    }

    @Transactional
    public void deletePhoto(int photoId, User user) {
        Photo photo = photoJPARepository.findById(photoId)
                .orElseThrow(() -> new Exception404("사진이 존재하지 않습니다."));

        if (photo.getUser().getId() == user.getId()) {
            photoJPARepository.delete(photo);
        }
    }
}
