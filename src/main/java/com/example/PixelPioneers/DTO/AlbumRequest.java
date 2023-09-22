package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AlbumRequest {
    @Getter
    @Setter
    public static class AlbumAddDTO {
        @NotBlank
        private String name;

        private List<Integer> userIdList;
    }

    @Getter
    @Setter
    public static class AlbumUpdateDTO {
        @NotBlank
        private String name;
    }

    @Getter
    @Setter
    public static class AlbumMemberUpdateDTO {
        @NotBlank
        private List<Integer> userIdList;
    }
}
