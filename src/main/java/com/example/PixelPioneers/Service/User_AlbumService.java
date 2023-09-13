package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.User_AlbumResponse;
import com.example.PixelPioneers.entity.User_Album;
import com.example.PixelPioneers.repository.AlbumJPARepository;

import com.example.PixelPioneers.repository.UserJPARepository;
import com.example.PixelPioneers.repository.User_AlbumJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@RequiredArgsConstructor
@Service
public class User_AlbumService {
    private final AlbumJPARepository albumJPARepository;
    private final UserJPARepository userJPARepository;
    private final User_AlbumJPARepository userAlbumJPARepository;

    @Transactional(readOnly = true)
    public List<User_AlbumResponse.FindAllDTO> User_Album_FindBy_Fk(int album_id){
        List<User_Album> User_AlbumList = albumJPARepository.findById(album_id).get().getUser_albums();
        List<User_AlbumResponse.FindAllDTO> responseDTOs = new ArrayList<>();

        for(User_Album item : User_AlbumList){
            responseDTOs.add(new User_AlbumResponse.FindAllDTO(item));
        }

        return responseDTOs;
    }

    @Transactional(readOnly = false)
    public void create_new(List<Integer> user_id_list, int album_id){
        List<User_Album> userAlbumList = new ArrayList<>();

        for(int i=0; i<user_id_list.size(); i++){
            User_Album userAlbum = User_Album.builder()
                    .user(userJPARepository.findById(user_id_list.get(i)).get())
                    .album(albumJPARepository.findById(album_id).get())
                    .build();

            userAlbumList.add(userAlbum);
        }
        userAlbumJPARepository.saveAll(userAlbumList);
    }

}



