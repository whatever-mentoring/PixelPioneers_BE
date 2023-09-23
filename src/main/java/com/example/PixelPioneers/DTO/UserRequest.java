package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.HashMap;

public class UserRequest {
    @Getter
    @Setter
    public static class EmailCheckDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;
    }

    @Getter
    @Setter
    public static class NicknameCheckDTO {
        @NotEmpty
        @Size(min = 1, max = 8, message = "8자 이내로 입력해주세요.")
        private String nickname;
    }

    @Getter
    @Setter
    public static class JoinDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;

        @NotEmpty
        @Size(min = 1, max = 8, message = "8자 이내로 입력해주세요.")
        private String nickname;

        public User toEntity(String imgURL) {
            return User.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .image(imgURL)
                    .role("ROLE_USER")
                    .build();
        }
    }

    @Getter
    @Setter
    public static class KaKaoJoinDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;

        @NotEmpty
        @Size(min = 1, max = 8, message = "8자 이내로 입력해주세요.")
        private String nickname;

        public User toEntity(String imgURL) {
            return User.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .image(imgURL)
                    .role("ROLE_USER")
                    .build();
        }

        public KaKaoJoinDTO(HashMap<String, Object> kakaoUser) {
            this.email = kakaoUser.get("email").toString();
            this.password = "-1";
            this.nickname = kakaoUser.get("nickname").toString();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;
    }

    @Getter
    @Setter
    public static class KaKaoLoginDTO {
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;

        public KaKaoLoginDTO(HashMap<String, Object> kakaoUser) {
            this.email = kakaoUser.get("email").toString();
            this.password = "-1";
        }
    }

    @Getter
    @Setter
    public static class UserProfileUpdateDTO {
        @NotEmpty
        private String nickname;
    }

    @Getter
    @Setter
    public static class UserPasswordUpdateDTO {
        @NotEmpty
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String currentPassword;

        @NotEmpty
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String newPassword;
    }

    @Getter
    @Setter
    public static class UserDeleteDTO {
        @NotEmpty
        @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문 대/소문자, 숫자, 특수문자를 포함해주세요.")
        private String password;
    }
}
