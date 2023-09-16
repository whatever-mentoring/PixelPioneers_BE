package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AlbumRequest {
    @Getter
    @Setter
    public static class AlbumAddDTO {
        @NotNull
        private String name;

        private String image;

        @NotNull
        private List<Integer> userIdList;
    }

    @Getter
    @Setter
    public static class AlbumUpdateDTO {
        @NotNull
        private String name;

        @NotNull
        private String image;
    }

    @Getter
    @Setter
    public static class AlbumMemberUpdateDTO {
        @NotNull
        private List<Integer> userIdList;
    }
}
