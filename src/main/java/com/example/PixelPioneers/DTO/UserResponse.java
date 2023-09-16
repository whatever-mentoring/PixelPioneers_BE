package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.User;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {
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
