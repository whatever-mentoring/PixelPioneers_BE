package com.example.PixelPioneers.repository;

import com.example.PixelPioneers.entity.Pose;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
/*

@Import(ObjectMapper.class)
@DataJpaTest
public class PoseJPARepositoryTest {
    @Autowired
    private PoseJPARepository poseJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void 설정() {
        List<Pose> poseList = new ArrayList<>();
        Pose pose1 = Pose.builder()
                .image("/images/1.jpg")
                .hashtag("#친구")
                .peopleCount(7)
                .build();
        Pose pose2 = Pose.builder()
                .image("/images/2.jpg")
                .hashtag("#가족")
                .peopleCount(4)
                .build();
        Pose pose3 = Pose.builder()
                .image("/images/3.jpg")
                .hashtag("#연인")
                .peopleCount(2)
                .build();
        Pose pose4 = Pose.builder()
                .image("/images/4.jpg")
                .hashtag("#썸")
                .peopleCount(2)
                .build();
        poseList.add(pose1);
        poseList.add(pose2);
        poseList.add(pose3);
        poseList.add(pose4);

        poseJPARepository.saveAll(poseList);
    }

    @Test
    public void 포즈_전체_조회() throws JsonProcessingException {
        // given

        // when
        List<Pose> poseList = poseJPARepository.findAll();

        // then
        assertThat(poseList.size()).isEqualTo(4);

//        String responseBody = om.writeValueAsString(poseList);
//        System.out.println("responseBody: " + responseBody);
    }

    @Test
    public void 인원수에_따른_포즈_전체_조회() throws JsonProcessingException {
        // given
        int peopleCount = 2;

        // when
        List<Pose> poseListByPeopleCount = poseJPARepository.findAllByPeopleCount(peopleCount);

        // then
//        assertThat(poseListByPeopleCount.get(0).getId()).isEqualTo(3); // 오류 발생: 메소드의 무작위 실행 순서로 인한 오류
        assertThat(poseListByPeopleCount.size()).isEqualTo(2);
        assertThat(poseListByPeopleCount.get(0).getPeopleCount()).isEqualTo(2);

//        String responseBody = om.writeValueAsString(poseListByPeopleCount);
//        System.out.println("responseBody:" + responseBody);
    }

    @Test
    public void 포즈_개별_조회() throws JsonProcessingException {
        // given
        int id = 4;

        // when
        Optional<Pose> pose = poseJPARepository.findById(id);

        // then
        assertThat(pose.get().getId()).isEqualTo(4);
        assertThat(pose.get().getImage()).isEqualTo("/images/4.jpg");
        assertThat(pose.get().getHashtag()).isEqualTo("#썸");
        assertThat(pose.get().getPeopleCount()).isEqualTo(2);

//        String responseBody = om.writeValueAsString(pose.get());
//        System.out.println("responseBody:" + responseBody);
    }
}
*/
