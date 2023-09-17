package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.repository.AlbumJPARepository;

import com.example.PixelPioneers.repository.UserJPARepository;
import com.example.PixelPioneers.repository.User_AlbumJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class User_AlbumService {
}



