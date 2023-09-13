package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.AlbumResponse;
import com.example.PixelPioneers.DTO.Photo_AlbumResponse;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.User_Photo;
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


@RequiredArgsConstructor
@Service
public class AlbumService {
    private final AlbumJPARepository albumRepository;

    @Transactional(readOnly = true)
    public List<AlbumResponse.FindAllDTO> findAll() {
        List<AlbumResponse.FindAllDTO> responseDTOs = albumRepository.findAll().stream()
                .map(album -> new AlbumResponse.FindAllDTO(album))
                .collect(Collectors.toList());

        return responseDTOs;
    }

    @Transactional(readOnly = true)
    public AlbumResponse.FindByIdDTO findById(int album_id) {
        Optional<Album> album = albumRepository.findById(album_id);

        return new AlbumResponse.FindByIdDTO(album);
    }
    @Transactional(readOnly = false)
    public AlbumResponse.FindByIdDTO create(Album new_album){
        albumRepository.save(new_album);
        Optional<Album> album = albumRepository.findById(new_album.getAlbum_id());

        return new AlbumResponse.FindByIdDTO(album);
    }

    @Transactional(readOnly = true)
    public List<Photo_AlbumResponse.FindAllDTO> Photo_FindBy_Fk(int album_id){
//        List<Photo> photoList = albumRepository.findById(album_id).get().getPhotos();
        List<Photo_AlbumResponse.FindAllDTO> responseDTOs = new ArrayList<>();

//        for(Photo item : photoList){
//            responseDTOs.add(new Photo_AlbumResponse.FindAllDTO(item));
//        }

        return responseDTOs;
    }

}
