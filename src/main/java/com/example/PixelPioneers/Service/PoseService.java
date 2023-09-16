package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.PoseResponse;
import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.repository.PoseJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RequiredArgsConstructor
@Service
public class PoseService {
    private final PoseJPARepository poseJPARepository;

    /**
     * 포즈 모아보기
     * 특정 인원수 유저 포즈 조회
     * 반환값: Pose 리스트
     */
    public PoseResponse.PoseListDTO userPoseListByPeopleCount(int peopleCount) {
        List<Pose> responseDTOs = new ArrayList<>();
        List<Pose> userPoses = new ArrayList<>();

        List<Pose> poses = poseJPARepository.findAll();

        /*for(Pose pose : poses){
            if(pose.getPhoto().getAlbum().getCreated_by == 1){ // 1이면 유저
                userPoses.add(pose);
            }
        }*/

        if(peopleCount == 0){
            for(Pose pose : poses){
                if(pose.getPhoto().getIs_public() == 1){
                    responseDTOs.add(pose);
                }
            }
        }
        else{
            for(Pose pose : poses){
                if(pose.getPhoto().getIs_public() == 1 && pose.getPeopleCount() == peopleCount) {
                    responseDTOs.add(pose);
                }
            }
        }

        return new PoseResponse.PoseListDTO(responseDTOs);
    }

    /**
     * 포즈 모아보기
     * 특정 인원수 관리자 포즈 조회
     * 반환값: Pose 리스트
     */
    public PoseResponse.PoseListDTO adminPoseListByPeopleCount(int peopleCount) {
        List<Pose> responseDTOs = new ArrayList<>();
        List<Pose> adminPoses = new ArrayList<>();

        List<Pose> poses = poseJPARepository.findAll();
        /*for(Pose pose : poses){
            if(pose.getPhoto().getAlbum().getCreated_by() == 0) { // 0이면 관리자
                adminPoses.add(pose);
            }
        }*/

        if(peopleCount == 0){
            for(Pose pose : poses){
                    responseDTOs.add(pose);
            }
        }
        else{
            for(Pose pose : poses){
                if(pose.getPeopleCount() == peopleCount){
                    responseDTOs.add(pose);
                }
            }
        }

        return new PoseResponse.PoseListDTO(responseDTOs);
    }

    /**
     * 랜덤 포즈 조회
     * 인원 수에 따른 랜덤 포즈 개별 조회
     */
    public PoseResponse.PoseDTO randomPoseByPeopleCount(int peopleCount) {
        List<Pose> responseDTOs = new ArrayList<>();

        List<Pose> poses = poseJPARepository.findAll(); // List<Pose> poses = {관리자가 올린 사진을 가져오는 코드} 로 수정하기

        for(Pose pose : poses){
            if(pose.getPeopleCount() == peopleCount){
                responseDTOs.add(pose);
            }
        }

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int randomIndex = random.nextInt(poses.size());
        Pose randomPose = responseDTOs.get(randomIndex);

        return new PoseResponse.PoseDTO(randomPose);
    }

    /**
     * 포즈 모아보기
     * 포즈 등록
     * - 사진이 최초 등록된 경우 동시에 수행
     */
    @Transactional(readOnly = false)
    public Pose create_new() {
        Pose pose = Pose.builder()
                .build();

        poseJPARepository.save(pose);

        return pose;
    }

    /**
     * 포즈 모아보기
     * 포즈 삭제
     * - 연결된 사진이 삭제된 경우 동시에 수행
     */
    public void delete(int id){
        poseJPARepository.deleteById(id);
    }
}
