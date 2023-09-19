package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.User;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {
    @Getter
    @Setter
    public static class LoginDTO {
        private UserDetailDTO userDetailDTO;
        private String JWTToken;

        public LoginDTO(User user, String JWTToken) {
            this.userDetailDTO = new UserDetailDTO(user);
            this.JWTToken = JWTToken;
        }

        @Getter
        @Setter
        public class UserDetailDTO {
            private int id;
            private String email;
            private String nickname;
            private String image;

            public UserDetailDTO(User user) {
                this.id = user.getId();
                this.email = user.getEmail();
                this.nickname = user.getNickname();
                this.image = user.getImage();
            }
        }
    }

    @Getter
    @Setter
    public static class UserListDTO {
        private int id;
        private String nickname;
        private String image;

        public UserListDTO(User user) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.image = user.getImage();
        }
    }
}
