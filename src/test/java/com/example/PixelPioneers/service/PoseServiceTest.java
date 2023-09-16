/*
package com.example.PixelPioneers.service;

import com.example.PixelPioneers.DTO.PoseResponse;
import com.example.PixelPioneers.Service.PoseService;
import com.example.PixelPioneers.entity.Pose;
import com.example.PixelPioneers.repository.PoseJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class PoseServiceTest {
    @Mock
    private PoseJPARepository poseJPARepository;

    @InjectMocks
    private PoseService poseService;

    private List<Pose> poseList;
    private List<Pose> poseListByPeopleCount;
    private Pose pose1;

    //@BeforeEach
    //public void 설정() {
    //    poseList = new ArrayList<>();
    //    poseListByPeopleCount = new ArrayList<>();

    //    pose1 = Pose.builder()
    //            .id(1)
    //            .image("/images/1.jpg")
    //            .hashtag("#친구")
    //            .peopleCount(7)
    //            .build();
    //    Pose pose2 = Pose.builder()
    //            .id(2)
    //            .image("/images/2.jpg")
    //            .hashtag("#가족")
    //            .peopleCount(4)
    //            .build();
    //    Pose pose3 = Pose.builder()
    //            .id(3)
    //            .image("/images/3.jpg")
    //            .hashtag("#연인")
    //            .peopleCount(2)
    //            .build();
    //    Pose pose4 = Pose.builder()
    //            .id(4)
    //            .image("/images/4.jpg")
    //            .hashtag("#썸")
    //            .peopleCount(2)
    //            .build();

    //    poseList.add(pose1);
    //    poseList.add(pose2);
    //    poseList.add(pose3);
    //    poseList.add(pose4);

    //    poseListByPeopleCount.add(pose3);
    //    poseListByPeopleCount.add(pose4);
    //}

    //@Test
    //public void 포즈_전체_조회() {
        // given
    //    int peopleCount = 0;
    //    given(poseJPARepository.findAll()).willReturn(poseList);

        // when
    //    PoseResponse.PoseListDTO responseDTOs = poseService.poseListByPeopleCount(peopleCount);

        // then
    //    assertThat(responseDTOs.getPoseList().size()).isEqualTo(4);
    //}

    //@Test
    //public void 인원_수에_따른_포즈_전체_조회() {
        // given
    //    int peopleCount = 2;
    //    given(poseJPARepository.findAllByPeopleCount(peopleCount)).willReturn(poseListByPeopleCount);

        // when
    //    PoseResponse.PoseListDTO responseDTOs = poseService.poseListByPeopleCount(peopleCount);

        // then
    //    assertThat(responseDTOs.getPoseList().size()).isEqualTo(2);
    //    assertThat(responseDTOs.getPoseList().get(0).getPeopleCount()).isEqualTo(2);
    //}

    //@Test
    //public void 인원_수에_따른_랜덤_포즈_개별_조회() {
        // given
    //    int peopleCount = 2;
    //    given(poseJPARepository.findAllByPeopleCount(peopleCount)).willReturn(poseListByPeopleCount);

        // when
    //    PoseResponse.PoseDetailDTO responseDTO = poseService.randomPoseDetailByPeopleCount(peopleCount);

        // then
    //    assertThat(responseDTO.getPeopleCount()).isEqualTo(2);
    //}

    //@Test
    //public void 인원_수에_따른_포즈_개별_조회() {
        // given
    //    int id = 1;
    //    given(poseJPARepository.findById(1)).willReturn(Optional.ofNullable(pose1));

        // when
    //    PoseResponse.PoseDetailDTO responseDTO = poseService.poseDetail(id);

        // then
    //    assertThat(responseDTO.getId()).isEqualTo(1);
    //    assertThat(responseDTO.getImage()).isEqualTo("/images/1.jpg");
    //    assertThat(responseDTO.getHashtag()).isEqualTo("#친구");
    //    assertThat(responseDTO.getPeopleCount()).isEqualTo(7);
    //}
}
*/
