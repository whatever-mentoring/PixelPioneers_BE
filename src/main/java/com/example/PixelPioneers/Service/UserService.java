package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.UserRequest;
import com.example.PixelPioneers.DTO.UserResponse;
import com.example.PixelPioneers.config.errors.exception.Exception400;
import com.example.PixelPioneers.config.errors.exception.Exception404;
import com.example.PixelPioneers.config.jwt.JWTTokenProvider;
import com.example.PixelPioneers.entity.User;
import com.example.PixelPioneers.repository.UserJPARepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    // S3 이미지 업로드 부분
    @Autowired
    private S3Uploader s3Uploader;
    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;

    public void emailCheck(String email) {
        Optional<User> optionalUser = userJPARepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new Exception400("동일한 이메일이 존재합니다.");
        }
    }

    public void nicknameCheck(String nickname) {
        Optional<User> optionalUser = userJPARepository.findByNickname(nickname);
        if (optionalUser.isPresent()) {
            throw new Exception400("동일한 닉네임이 존재합니다.");
        }
    }

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO, MultipartFile file) throws Exception {
        emailCheck(requestDTO.getEmail());
        nicknameCheck(requestDTO.getNickname());

        String imgURL = s3Uploader.upload(file, "user_profile");

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        User user = userJPARepository.save(requestDTO.toEntity(imgURL));
    }

    public String login(UserRequest.LoginDTO requestDTO) {
        User user = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new Exception400("잘못된 이메일입니다.")
        );

        if(!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())){
            throw new Exception400("잘못된 비밀번호입니다.");
        }
        return JWTTokenProvider.create(user);
    }

    public String getKakaoAccessToken(String code) {
        String access_token = "";
        String refresh_token = "";
        String requestURL = "https://kauth.kakao.com/login/kakao";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=b59aee3993ebe9ad7fbb5727b2539f35");
            sb.append("&redirect_uri=http://localhost:8080/login/kakao");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token: " + access_token);
            System.out.println("refresh_token: " + refresh_token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_token;
    }

    public void kakaoLogin(String access_token) throws Exception {
        String requestURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorizatioin",  "Bearer " + access_token);

            int responseCode = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();

            if (hasEmail)  {
                String email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }


            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<UserResponse.UserListDTO> findUserList(UserRequest.UserListDTO requestDTO) {
        List<User> userList = userJPARepository.findByNicknameStartingWith(requestDTO.getNickname());

        List<UserResponse.UserListDTO> responseDTOs = userList.stream()
                .map(user -> new UserResponse.UserListDTO(user))
                .collect(Collectors.toList());
        return responseDTOs;
    }

    @Transactional
    public UserResponse.UserListDTO updateUser(int id, UserRequest.UserUpdateDTO updateDTO, MultipartFile file, User sessionUser) throws Exception {
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("사용자가 존재하지 않습니다."));

        if (user.getId() == sessionUser.getId()) {
            // 기존 사용자 프로필사진 S3에서 삭제
            String imgURL = user.getImage();
            String key = imgURL.substring(61);
            s3Uploader.deleteFile(key);
            // 사용자 프로필사진 변경
            String new_imgURL = s3Uploader.upload(file, "user_profile");

            user.update(updateDTO.getNickname(), new_imgURL);
        }
        return new UserResponse.UserListDTO(user);
    }
}
