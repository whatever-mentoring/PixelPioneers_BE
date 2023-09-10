package com.example.Posewithme.controller;

import com.example.Posewithme.service.PoseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {PoseRESTController.class})
public class PoseRESTControllerTest {
    @MockBean
    private PoseService poseService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void 설정() {}

    @Test
    public void 포즈_전체_조회() {}

    @Test
    public void 인원_수에_따른_포즈_전체_조회() {}

    @Test
    public void 인원_수에_따른_랜덤_포즈_개별_조회() {}

    @Test
    public void 인원_수에_따른_포즈_개별_조회() {}
}
