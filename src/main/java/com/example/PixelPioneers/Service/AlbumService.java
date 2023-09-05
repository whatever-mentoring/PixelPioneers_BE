package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.AlbumResponse;
import com.example.PixelPioneers.DTO.Album_PhotoResponse;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.repository.AlbumJPARepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AlbumService {
    private final AlbumJPARepository albumRepository;

    public List<AlbumResponse.FindAllDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Album> pageContent = albumRepository.findAll(pageable);

        List<AlbumResponse.FindAllDTO> responseDTOs = pageContent.getContent().stream()
                .map(album -> new AlbumResponse.FindAllDTO(album))
                .collect(Collectors.toList());
        return responseDTOs;
    }

    public AlbumResponse.FindByIdDTO findById(int album_id) {
        Optional<Album> album = albumRepository.findById(album_id);
        return new AlbumResponse.FindByIdDTO(album);
    }

    public AlbumResponse.FindByIdDTO create(Album new_album){
        albumRepository.save(new_album);
        Optional<Album> album = albumRepository.findById(new_album.getAlbum_id());
        return new AlbumResponse.FindByIdDTO(album);
    }

    public List<Album_PhotoResponse.FindAllDTO> Photo_FindBy_Fk(int album_id){
        List<Photo> photoList = albumRepository.findById(album_id).get().getPhotos();
        List<Album_PhotoResponse.FindAllDTO> responseDTOs = new ArrayList<>();

        for(Photo item : photoList){
            responseDTOs.add(new Album_PhotoResponse.FindAllDTO(item));
        }

        return responseDTOs;
    }

}
