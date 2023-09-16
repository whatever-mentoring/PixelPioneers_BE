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
}
