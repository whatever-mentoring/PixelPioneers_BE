package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.PoseResponse;
import com.example.PixelPioneers.config.errors.exception.Exception404;
import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.repository.PoseJPARepository;
import com.example.PixelPioneers.repository.User_AlbumJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class PoseService {

    private final User_AlbumJPARepository user_albumJPARepository;
    private final PoseJPARepository poseJPARepository;

    /**
     * 랜덤 포즈 조회
     * 인원 수에 따른 랜덤 포즈 개별 조회
     */
    public PoseResponse.PoseDTO randomPoseDetailByPeopleCount(int peopleCount) {
        List<Integer> albumIdList = user_albumJPARepository.findAlbumByRole("ROLE_ADMIN");
        List<Pose> poseList = poseJPARepository.findByAlbumIdAndPeopleCount(albumIdList, peopleCount);

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        int randomIndex = random.nextInt(poseList.size());
        Pose randomPose = poseList.get(randomIndex);

        return new PoseResponse.PoseDTO(randomPose);
    }

    /**
     * 포즈 모아보기
     * 카테고리와 인원 수를 선택했을 경우, 카테고리와 인원 수에 따른 포즈 전체 조회
     */
    public List<PoseResponse.PoseDTO> poseListByCategoryAndPeopleCount(String category, int peopleCount) {
        List<Integer> albumIdList = user_albumJPARepository.findAlbumByRole(category);
        List<Pose> poseList = poseJPARepository.findByAlbumIdAndPeopleCount(albumIdList, peopleCount);

        List<Pose> openPoseList = new ArrayList<>();
        for (Pose pose : poseList) {
            if (pose.getPhoto().isOpen()) {
                openPoseList.add(pose);
            }
        }

        List<PoseResponse.PoseDTO> responseDTOs = openPoseList.stream()
                .map(pose -> new PoseResponse.PoseDTO(pose))
                .collect(Collectors.toList());

        return responseDTOs;
    }

    public PoseResponse.PoseDTO findPoseDetail(int id) {
        Pose pose = poseJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("포즈가 존재하지 않습니다."));

        return new PoseResponse.PoseDTO(pose);
    }

}