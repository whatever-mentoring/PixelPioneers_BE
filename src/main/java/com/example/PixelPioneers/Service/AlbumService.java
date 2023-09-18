package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.*;

import com.example.PixelPioneers.config.errors.exception.Exception400;
import com.example.PixelPioneers.config.errors.exception.Exception404;
import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.entity.User;
import com.example.PixelPioneers.entity.User_Album;
import com.example.PixelPioneers.repository.AlbumJPARepository;
import com.example.PixelPioneers.repository.PhotoJPARepository;
import com.example.PixelPioneers.repository.UserJPARepository;
import com.example.PixelPioneers.repository.User_AlbumJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AlbumService {
    private final UserJPARepository userJPARepository;
    private final User_AlbumJPARepository user_albumJPARepository;
    private final AlbumJPARepository albumJPARepository;
    private final PhotoJPARepository photoJPARepository;

    // S3 이미지 업로드 부분
    @Autowired
    private S3Uploader s3Uploader;

//    public AlbumResponse.FindByIdDTO findById(int album_id) {
//        Optional<Album> album = albumRepository.findById(album_id);
//        return new AlbumResponse.FindByIdDTO(album);
//    }

    public void addAlbum(AlbumRequest.AlbumAddDTO requestDTO, MultipartFile file, User sessionUser) throws Exception {
        String imgurl = s3Uploader.upload(file, "album_cover");

        String name = requestDTO.getName();
        String image = imgurl;
        Album newAlbum = Album.builder().name(name).image(image).build();
        Album album = albumJPARepository.save(newAlbum);

        List<Integer> userIdList = requestDTO.getUserIdList();
        for (Integer userId: userIdList) {
            User user = userJPARepository.findById(userId)
                    .orElseThrow(() -> new Exception404("사용자가 존재하지 않습니다."));
            User_Album newUser_Album = User_Album.builder().user(user).album(album).build();
            user_albumJPARepository.save(newUser_Album);
        }
    }

    public List<AlbumResponse.AlbumDTO> findAlbumListByUser(User user, int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Album> pageContent = user_albumJPARepository.findAlbumByUserId(user.getId(), pageable);

        List<AlbumResponse.AlbumDTO> responseDTOs = pageContent.getContent().stream()
                .map(album -> new AlbumResponse.AlbumDTO(album))
                .collect(Collectors.toList());

        return responseDTOs;
    }
    public PhotoResponse.PhotoListDTO findPhotoList(int id, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Photo> pageContent = photoJPARepository.findByAlbumId(id, pageable);

        List<Photo> photos = new ArrayList<>();

        photos = pageContent.getContent().stream()
                .map(photo -> new Photo(photo))
                .collect(Collectors.toList());

        return new PhotoResponse.PhotoListDTO(photos);
    }

    @Transactional
    public AlbumResponse.AlbumDTO updateAlbum(int id, AlbumRequest.AlbumUpdateDTO updateDTO, MultipartFile file) throws Exception {
        Album album = albumJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("사진첩이 존재하지 않습니다."));

        // 기존 사진첩 대표사진 S3에서 삭제
        String imgURL = albumJPARepository.findById(id).get().getImage();
        String key = imgURL.substring(61);
        s3Uploader.deleteFile(key);
        // 사진첩 대표사진 변경
        String new_imgurl = s3Uploader.upload(file, "album_cover");

        album.update(updateDTO.getName(), new_imgurl);
        return new AlbumResponse.AlbumDTO(album);
    }

    public List<UserResponse.UserListDTO> findAlbumMemberByAlbum(int id) {
        List<User> albumMemberList = user_albumJPARepository.findUserByAlbum(id);

        List<UserResponse.UserListDTO> responseDTOs = albumMemberList.stream()
                .map(user -> new UserResponse.UserListDTO(user))
                .collect(Collectors.toList());
        return responseDTOs;
    }

    public void addAlbumMember(int id, AlbumRequest.AlbumMemberUpdateDTO updateDTO) {
        Album album = albumJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("사진첩이 존재하지 않습니다."));

        List<User_Album> user_albumList = new ArrayList<>();
        for (int userId: updateDTO.getUserIdList()) {
            User user = userJPARepository.findById(userId)
                    .orElseThrow(() -> new Exception404("사용자가 존재하지 않습니다."));
            User_Album newUser_Album = User_Album.builder().user(user).album(album).build();
            user_albumList.add(newUser_Album);
        }

        user_albumJPARepository.saveAll(user_albumList);
    }

    public void deleteAlbumMember(int id, User sessionUser) {
        user_albumJPARepository.deleteByUserIdAndAlbumId(id, sessionUser.getId());
    }
}
