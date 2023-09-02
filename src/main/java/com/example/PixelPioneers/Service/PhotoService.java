package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.entity.Photo;
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
    private final PhotoJPARepository photoRepository;

    public List<PhotoResponse.FindAllDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Photo> pageContent = photoRepository.findAll(pageable);

        List<PhotoResponse.FindAllDTO> responseDTOs = pageContent.getContent().stream()
                .map(photo -> new PhotoResponse.FindAllDTO(photo))
                .collect(Collectors.toList());
        return responseDTOs;
    }

    public PhotoResponse.FindByIdDTO findById(int photo_id) {
        Optional<Photo> photo = photoRepository.findById(photo_id);
        return new PhotoResponse.FindByIdDTO(photo);
    }

    public PhotoResponse.FindByIdDTO create(Photo new_photo){
        photoRepository.save(new_photo);
        Optional<Photo> photo = photoRepository.findById(new_photo.getPhoto_id());
        return new PhotoResponse.FindByIdDTO(photo);
    }
}
