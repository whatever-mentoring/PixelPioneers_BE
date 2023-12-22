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

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class PoseService {

    private final User_AlbumJPARepository user_albumJPARepository;
    private final PoseJPARepository poseJPARepository;

    private int index = 0;


    /**
     * 랜덤 포즈 조회
     * 인원 수에 따른 랜덤 포즈 개별 조회
     */
    public PoseResponse.PoseDTO randomPoseDetailByPeopleCount(int peopleCount) {
        List<Pose> poseList = poseJPARepository.findByPeopleCountAndOpenAndPass(peopleCount, true, "ACCEPT");
        List<Integer> randomIndexList = makeRandomIndexList(10, poseList.size());

        if (index == 10 - 1) {
            index = 0;
            randomIndexList = makeRandomIndexList(10, poseList.size());
        }

        int randomIndex = randomIndexList.get(index++);
        Pose randomPose = poseList.get(randomIndex);

        return new PoseResponse.PoseDTO(randomPose);
    }

    private List<Integer> makeRandomIndexList(int size, int max) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        List<Integer> randomIndexList = new ArrayList<>(size);
        Set<Integer> randomIndexSet = new HashSet<>(size);

        while (true) {
            int randomNum = random.nextInt(max);
            if (randomIndexSet.contains(randomNum))
                continue;
            randomIndexList.add(randomNum);
            randomIndexSet.add(randomNum);
            if (randomIndexSet.size() == size)
                break;
        }

        return randomIndexList;
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