package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.AlbumResponse;
import com.example.PixelPioneers.DTO.PoseResponse;
import com.example.PixelPioneers.config.errors.exception.Exception404;
import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.repository.PhotoJPARepository;
import com.example.PixelPioneers.repository.PoseJPARepository;
import com.example.PixelPioneers.repository.User_AlbumJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class PoseService {

    private final User_AlbumJPARepository user_albumJPARepository;
    private final PoseJPARepository poseJPARepository;

    private List<Integer> randomIndexList = new ArrayList<>();

    /**
     * 랜덤 포즈 조회
     * 인원 수에 따른 랜덤 포즈 개별 조회
     */
    public PoseResponse.PoseDTO randomPoseDetailByPeopleCount(int peopleCount) {
        List<Pose> poseList = poseJPARepository.findByPeopleCountAndOpenAndPass(peopleCount, true, "ACCEPT");

        if (randomIndexList.size() == 10) {
            randomIndexList.clear();
        }

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        int randomIndex;
        while (true) {
            randomIndex = random.nextInt(poseList.size());
            if (!randomIndexList.contains(randomIndex)) {
                randomIndexList.add(randomIndex);
                break;
            }
        }
        Pose randomPose = poseList.get(randomIndex);

        return new PoseResponse.PoseDTO(randomPose);
    }

    /**
     * 포즈 모아보기
     * 카테고리와 인원 수를 선택했을 경우, 카테고리와 인원 수에 따른 포즈 전체 조회
     */
    public List<PoseResponse.PoseDTO> poseListByCategoryAndPeopleCount(String category, int peopleCount, int page) {
        Pageable pageable = PageRequest.of(page, 10);

        List<Integer> albumIdList = user_albumJPARepository.findAlbumByRole(category);
        Page<Pose> pageContent = poseJPARepository.findByAlbumIdAndPeopleCountAndOpenAndPass(albumIdList, peopleCount, true, "ACCEPT", pageable);

        List<PoseResponse.PoseDTO> responseDTOs = pageContent.getContent().stream()
                .map(pose -> new PoseResponse.PoseDTO(pose))
                .collect(Collectors.toList());

        return responseDTOs;
    }

    public PoseResponse.PoseDTO findPoseDetail(int id) {
        Pose pose = poseJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("포즈가 존재하지 않습니다."));

        pose.updateViewCount();

        return new PoseResponse.PoseDTO(pose);
    }
}