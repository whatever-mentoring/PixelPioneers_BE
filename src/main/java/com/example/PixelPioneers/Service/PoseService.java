package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.PoseResponse;
import com.example.PixelPioneers.DTO.User_PhotoResponse;
import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.entity.User_Photo;
import com.example.PixelPioneers.repository.PoseJPARepository;
import com.example.PixelPioneers.repository.User_PhotoJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PoseService {
    private final PoseJPARepository poseJPARepository;
    private final User_PhotoJPARepository userPhotoJPARepository;

    /**
     * 포즈 모아보기
     * 포즈 전체 조회
     * 전체공개 포즈만 조회
     * 포즈 리스트 반환
     */
    @Transactional(readOnly = true)
    public PoseResponse.PoseListDTO manager_poseList() {
        List<Pose> Poses = poseJPARepository.findAll();
        List<Pose> responseDTOs = new ArrayList<>();
        for(Pose item : Poses){
            // 전체공개인 포즈만 선택
            if(item.getUserPhoto().getPhoto_public() == 1){
                responseDTOs.add(item);
            }
        }
        return new PoseResponse.PoseListDTO(responseDTOs);
    }

    /**
     * 포즈 모아보기
     * 인원 수를 선택했을 경우, 인원 수에 따른 포즈 전체 조회
     * 포즈 리스트 X.사진 리스트 반환
     */
    public List<User_PhotoResponse.FindAllDTO> poseListByPeopleCount(int peopleCount) {
        List<User_PhotoResponse.FindAllDTO> responseDTOs = new ArrayList<>();
        List<Pose> poses = poseJPARepository.findAll();
        if(peopleCount == 0){   // 전체공개 포즈 전체 조회
            for(Pose pose : poses){
                if(pose.getUserPhoto().getPhoto_public() == 1){
                    responseDTOs.add(new User_PhotoResponse.FindAllDTO(pose.getUserPhoto()));
                }
            }
        }
        else{   // 전체공개 포즈 중 선택한 인원수 포즈만 조회
            List<User_Photo> photos = userPhotoJPARepository.findByPeopleCount(peopleCount);
            for(User_Photo photo : photos){
                if(photo.getPhoto_public() == 1) {
                    responseDTOs.add(new User_PhotoResponse.FindAllDTO(photo));
                }
            }
        }

        return responseDTOs;
    }

    /**
     * 랜덤 포즈 조회
     * 인원 수에 따른 랜덤 포즈 개별 조회
     */
    //public PoseResponse.PoseDetailDTO randomPoseDetailByPeopleCount(int peopleCount) {
    //    List<Pose> responseDTOs = poseJPARepository.findAllByPeopleCount(peopleCount);

     //   Random random = new Random();
     //   random.setSeed(System.currentTimeMillis());

     //   int randomIndex = random.nextInt(responseDTOs.size());
     //   Pose randomPose = responseDTOs.get(randomIndex);

    //    return new PoseResponse.PoseDetailDTO(randomPose);
    //}

    /**
     * 포즈 모아보기
     * 포즈 등록
     * - 사진이 최초 등록된 경우 동시에 수행
     */
    @Transactional(readOnly = false)
    public Pose create_new(int category) {

        Pose pose = Pose.builder()
                .pose_category(category)   // 1이면 사용자가 올린 사진. 0이면 관리자가 올린 사진
                .build();

        poseJPARepository.save(pose);

        return pose;
    }

    /**
     * 포즈 모아보기
     * 포즈 삭제
     * - 연결된 사진이 삭제된 경우 동시에 수행
     */
    public void delete(int pose_id){
        poseJPARepository.deleteById(pose_id);
    }
}
