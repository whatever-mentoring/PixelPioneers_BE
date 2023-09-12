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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@RequiredArgsConstructor
@Service
public class PoseService {
    private final PoseJPARepository poseJPARepository;

     /**
     * 포즈 모아보기
     * 인원 수를 선택하지 않았을 경우, 포즈 전체 조회
     */
     @Transactional(readOnly = true)
     public PoseResponse.PoseListDTO poseList() {
        List<Pose> responseDTOs = poseJPARepository.findAll();
        return new PoseResponse.PoseListDTO(responseDTOs);
    }

    /**
     * 포즈 모아보기
     * 인원 수를 선택했을 경우, 인원 수에 따른 포즈 전체 조회
     */
    //public PoseResponse.PoseListDTO poseListByPeopleCount(int peopleCount) {
    //    List<Pose> responseDTOs;
    //    if (peopleCount == 0) {
     //       responseDTOs = poseJPARepository.findAll();
     //   }
    //    else {
    //        responseDTOs = poseJPARepository.findAllByPeopleCount(peopleCount);
    //    }
    //    return new PoseResponse.PoseListDTO(responseDTOs);
    //}

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
     * 포즈 개별 조회
     */
    @Transactional(readOnly = true)
    public PoseResponse.PoseDetailDTO poseDetail(int id) {
        Pose responseDTO = poseJPARepository.findById(id).get();
        return new PoseResponse.PoseDetailDTO(responseDTO);
    }

    /**
     * 포즈 모아보기
     * 포즈 등록
     */
    @Transactional(readOnly = false)
    public Pose create_new(User_Photo userPhoto) {

        Pose pose = Pose.builder()
                .userPhoto(userPhoto)
                .build();

        poseJPARepository.save(pose);

        return pose;
    }

    public void deleteByPhoto(User_Photo userPhoto){
        Pose pose = poseJPARepository.findByUserPhoto(userPhoto);
        poseJPARepository.delete(pose);
    }
}