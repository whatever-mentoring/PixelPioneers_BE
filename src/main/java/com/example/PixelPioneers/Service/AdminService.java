package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.AdminRequest;
import com.example.PixelPioneers.DTO.KakaoUserInfoResponse;
import com.example.PixelPioneers.DTO.PhotoResponse;
import com.example.PixelPioneers.config.errors.exception.Exception404;
import com.example.PixelPioneers.entity.Photo;
import com.example.PixelPioneers.entity.User;
import com.example.PixelPioneers.repository.PhotoJPARepository;
import com.example.PixelPioneers.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class AdminService {

    private final PhotoJPARepository photoJPARepository;
    private final UserService userService;
    private final UserJPARepository userJPARepository;

    public List<PhotoResponse.PhotoListDTO> findRequestPhotoList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Photo> pageContent = photoJPARepository.findByOpenAndPass(true, "PENDING", pageable);

        List<PhotoResponse.PhotoListDTO> responseDTOs = pageContent.getContent().stream()
                .map(photo -> new PhotoResponse.PhotoListDTO(photo))
                .collect(Collectors.toList());

        return responseDTOs;
    }

    public PhotoResponse.PhotoDetailDTO findRequestPhotoDetail(int photoId) {
        Photo photo = photoJPARepository.findById(photoId)
                .orElseThrow(() -> new Exception404("사진이 존재하지 않습니다."));

        return new PhotoResponse.PhotoDetailDTO(photo);
    }

    @Transactional
    public void reviewRequestPhoto(int photoId, AdminRequest.requestPhotoReviewDTO requestDTO) {
        Photo photo = photoJPARepository.findById(photoId)
                .orElseThrow(() -> new Exception404("사진이 존재하지 않습니다."));

        photo.updateIsPass(requestDTO.getPass());
    }

    @Transactional
    public void updateAllUserInfoByKakao() {
        List<String> kakaoUserIdList = userService.getAllKakaoUserIdList();

        for (String kakaoUserId : kakaoUserIdList) {
            KakaoUserInfoResponse kakaoUser = userService.getKakaoUserByAdminKeyAndUserId(kakaoUserId);
            Optional<User> user = userJPARepository.findByEmail(kakaoUser.getEmail());
            if (!user.isEmpty()) {
                if (kakaoUser.getAgeRange() != null)
                    user.get().updateAgeRange(kakaoUser.getAgeRange());
                if (kakaoUser.getGender() != null)
                    user.get().updateGender(kakaoUser.getGender());
            }
        }
    }
}
