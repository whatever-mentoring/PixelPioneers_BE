package com.example.PixelPioneers.service;
import com.example.PixelPioneers.entity.pose;
import com.example.PixelPioneers.repository.PoseRepository;
import com.example.PixelPioneers.response.PoseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PoseService {
    private final PoseRepository poseRepository;

    public List<PoseResponse.FindAllDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<pose> pageContent = poseRepository.findAll(pageable);

        List<PoseResponse.FindAllDTO> responseDTOs = pageContent.getContent().stream()
                .map(pose -> new PoseResponse.FindAllDTO(pose))
                .collect(Collectors.toList());
        return responseDTOs;
    }

    public PoseResponse.FindByIdDTO findById(int id) {
        Optional<pose> pose = poseRepository.findById(id);
        return new PoseResponse.FindByIdDTO(pose);
    }
}
