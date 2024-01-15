package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class AdminResponse {
    @Getter
    @Setter
    public static class UserInfoDTO {
        private int id;
        private String age_range;
        private String gender;
        private LocalDate created_at;

        public UserInfoDTO(User user) {
            this.id = user.getId();
            this.age_range = user.getAge_range();
            this.gender = user.getGender();
            this.created_at = user.getCreated_at();
        }
    }
}
