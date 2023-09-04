package com.example.Posewithme.Service;

import com.example.Posewithme.DTO.UserRequest;
import com.example.Posewithme.Entity.User;
import com.example.Posewithme.Repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        userJPARepository.save(requestDTO.toEntity());
    }

    public String login(UserRequest.LoginDTO requestDTO) {
        User user = userJPARepository.findByEmail(requestDTO.getEmail());

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {}

        return JWTProvider.create(user);
}
