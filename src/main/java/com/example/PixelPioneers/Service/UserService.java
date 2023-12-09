package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.UserRequest;
import com.example.PixelPioneers.DTO.UserResponse;
import com.example.PixelPioneers.config.errors.exception.Exception400;
import com.example.PixelPioneers.config.errors.exception.Exception404;
import com.example.PixelPioneers.config.jwt.JWTTokenProvider;
import com.example.PixelPioneers.entity.User;
import com.example.PixelPioneers.repository.UserJPARepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
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

    @Value("${kakao.restApiKey}")
    private String kakaoRestApiKey;

//    public void emailCheck(String email) {
//        Optional<User> optionalUser = userJPARepository.findByEmail(email);
//        if (optionalUser.isPresent()) {
//            throw new Exception400("동일한 이메일이 존재합니다.");
//        }
//    }

//    public void nicknameCheck(String nickname) {
//        Optional<User> optionalUser = userJPARepository.findByNickname(nickname);
//        if (optionalUser.isPresent()) {
//            throw new Exception400("동일한 닉네임이 존재합니다.");
//        }
//    }

//    @Transactional
//    public void join(UserRequest.JoinDTO requestDTO, MultipartFile file) throws Exception {
//        emailCheck(requestDTO.getEmail());
//        nicknameCheck(requestDTO.getNickname());
//
//        String imgURL = s3Uploader.upload(file, "user_profile");
//
//        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
//        userJPARepository.save(requestDTO.toEntity(imgURL));
//    }

    public UserResponse.LoginDTO kakaoSimpleLogin(String code) throws Exception {
        String access_token = getKakaoAccessToken(code);
        HashMap<String, Object> kakaoUser = getKakaoUser(access_token);
//        System.out.println(kakaoUser);

        Optional<User> user = userJPARepository.findByEmail(kakaoUser.get("email").toString());
//        System.out.println(user);
        if (user.isEmpty()) {
            UserRequest.KaKaoJoinDTO joinRequestDTO = new UserRequest.KaKaoJoinDTO(kakaoUser);
            kakaoJoin(joinRequestDTO);
        }
        UserRequest.KaKaoLoginDTO loginRequestDTO = new UserRequest.KaKaoLoginDTO(kakaoUser);
        UserResponse.LoginDTO responseDTO = kakaoLogin(loginRequestDTO);

        return responseDTO;
    }

    public String getKakaoAccessToken(String code) {
        String access_token = "";
        String refresh_token = "";
        String requestURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + kakaoRestApiKey);
            sb.append("&redirect_uri=http://moamoa4cut.net/Oauth");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = connection.getResponseCode();
            System.out.println("responseCode: " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body: " + result);

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

    public HashMap<String, Object> getKakaoUser(String access_token) {
        String requestURL = "https://kapi.kakao.com/v2/user/me";
        HashMap<String, Object> user = new HashMap<>();

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization",  "Bearer " + access_token);

            int responseCode = connection.getResponseCode();
            System.out.println("responseCode: " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body: " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String email = kakao_account.get("email").getAsString();
            String nickname = kakao_account.get("profile").getAsJsonObject().get("nickname").getAsString();
            String image = kakao_account.get("profile").getAsJsonObject().get("profile_image_url").getAsString();

            user.put("id", id);
            user.put("email", email);
            user.put("nickname", nickname);
            user.put("image", image);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Transactional
    public void kakaoJoin(UserRequest.KaKaoJoinDTO requestDTO) throws Exception {
        userJPARepository.save(requestDTO.toEntity());
    }

//    public UserResponse.LoginDTO login(UserRequest.LoginDTO requestDTO) {
//        User user = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
//                () -> new Exception400("잘못된 이메일입니다.")
//        );
//
//        if(!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())){
//            throw new Exception400("잘못된 비밀번호입니다.");
//        }
//        return new UserResponse.LoginDTO(user, JWTTokenProvider.create(user));
//    }

    public UserResponse.LoginDTO kakaoLogin(UserRequest.KaKaoLoginDTO requestDTO) {
        User user = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new Exception400("잘못된 이메일입니다.")
        );

        if(!requestDTO.getPassword().equals(user.getPassword())) {
            throw new Exception400("잘못된 비밀번호입니다.");
        }
        return new UserResponse.LoginDTO(user, JWTTokenProvider.create(user));
    }

    public List<UserResponse.UserListDTO> findUserList(String nickname, User sessionUser) {
        List<User> userList = userJPARepository.findByNicknameStartingWith(nickname);

        List<UserResponse.UserListDTO> responseDTOs = userList.stream()
                .filter(user -> !user.getRole().equals("ROLE_ADMIN"))
                .filter(user -> user.getId() != sessionUser.getId())
                .map(user -> new UserResponse.UserListDTO(user))
                .collect(Collectors.toList());
        return responseDTOs;
    }

    @Transactional
    public UserResponse.UserListDTO updateUserProfile(int id, UserRequest.UserProfileUpdateDTO updateDTO, MultipartFile file, User sessionUser) throws Exception {
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("사용자가 존재하지 않습니다."));

        if (user.getId() == sessionUser.getId()) {

            String new_nickName = user.getNickname();
            if (!updateDTO.getNickname().isBlank()) {
                new_nickName = updateDTO.getNickname();
            }

            String new_imgURL = user.getImage();
            if (!file.isEmpty()) {
                // 기존 사용자 프로필사진 S3에서 삭제
                String key = new_imgURL.substring(61);
                s3Uploader.deleteFile(key);
                // 사용자 프로필사진 변경
                new_imgURL = s3Uploader.upload(file, "user_profile");
            }

            user.updateUserProfile(new_nickName, new_imgURL);
        }
        return new UserResponse.UserListDTO(user);
    }

    @Transactional
    public void updateUserPassword(int id, UserRequest.UserPasswordUpdateDTO updateDTO, User sessionUser) throws Exception {
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("사용자가 존재하지 않습니다."));

        if (user.getId() == sessionUser.getId()) {
            if (passwordEncoder.matches(updateDTO.getCurrentPassword(), user.getPassword()) && !passwordEncoder.matches(updateDTO.getNewPassword(), user.getPassword())) {
                updateDTO.setNewPassword(passwordEncoder.encode(updateDTO.getNewPassword()));
                user.updatePassword(updateDTO.getNewPassword());
            }
        }
    }

    @Transactional
    public void deleteUser(int id, UserRequest.UserDeleteDTO requestDTO, User sessionUser) throws Exception {
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("사용자가 존재하지 않습니다."));

        if (user.getId() == sessionUser.getId()) {
            if (passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
                userJPARepository.deleteById(id);
            }
        }
    }
}