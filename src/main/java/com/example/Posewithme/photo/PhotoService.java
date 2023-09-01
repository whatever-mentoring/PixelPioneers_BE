package com.example.Posewithme.photo;

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

    public PhotoResponse.FindByIdDTO findById(int id) {
        Optional<Photo> photo = photoRepository.findById(id);
        return new PhotoResponse.FindByIdDTO(photo);
    }
}
