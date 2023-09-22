package com.example.PixelPioneers.Service;

import com.example.PixelPioneers.DTO.AlbumResponse;
import com.example.PixelPioneers.DTO.UserRequest;
import com.example.PixelPioneers.DTO.UserResponse;
import com.example.PixelPioneers.config.errors.exception.Exception400;
import com.example.PixelPioneers.config.jwt.JWTTokenProvider;
import com.example.PixelPioneers.entity.User;
import com.example.PixelPioneers.repository.UserJPARepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;

    public void emailCheck(String email) {
        Optional<User> optionalUser = userJPARepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new Exception400("동일한 이메일이 존재합니다.");
        }
    }

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        emailCheck(requestDTO.getEmail());

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        User user = userJPARepository.save(requestDTO.toEntity());
    }

    public UserResponse.LoginDTO login(UserRequest.LoginDTO requestDTO) {
        User user = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new Exception400("잘못된 이메일입니다.")
        );

        if(!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())){
            throw new Exception400("잘못된 비밀번호입니다.");
        }
        return new UserResponse.LoginDTO(user, JWTTokenProvider.create(user));
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
            sb.append("&client_id=b59aee3993ebe9ad7fbb5727b2539f35");
            sb.append("&redirect_uri=http://localhost:3000/login/kakao");
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

    public HashMap<String, Object> getKakaoUser(String access_token) throws Exception {
        String requestURL = "https://kapi.kakao.com/v2/user/me";
        HashMap<String, Object> user = new HashMap<>();

        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
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
            JsonObject profile = kakao_account.get("profile").getAsJsonObject();
//            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();


            String email = kakao_account.get("email").getAsString();
            String nickname = profile.get("nickname").getAsString();
            String image = profile.get("profile_image_url").getAsString();

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

    public List<UserResponse.UserListDTO> findUserList(UserRequest.UserListDTO requestDTO) {
        List<User> userList = userJPARepository.findByNicknameStartingWith(requestDTO.getNickname());

        List<UserResponse.UserListDTO> responseDTOs = userList.stream()
                .map(user -> new UserResponse.UserListDTO(user))
                .collect(Collectors.toList());
        return responseDTOs;
    }

//    public void kakoLogin(String access_token) {
//        User user = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
//                () -> new Exception400("잘못된 이메일입니다.")
//        );
//
//        if(!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())){
//            throw new Exception400("잘못된 비밀번호입니다.");
//        }
//        return new UserResponse.LoginDTO(user, JWTTokenProvider.create(user));
//    }
}
