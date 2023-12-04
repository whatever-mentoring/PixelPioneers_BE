package com.example.PixelPioneers.DTO;

import com.example.PixelPioneers.entity.Album;
import com.example.PixelPioneers.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AlbumRequest {
    @Getter
    @Setter
    public static class AlbumAddDTO {
        @NotEmpty
        private String name;

        private List<Integer> userIdList;
    }

    @Getter
    @Setter
    public static class AlbumUpdateDTO {
        private String name;
    }

    @Getter
    @Setter
    public static class AlbumMemberUpdateDTO {
        @NotNull
        private List<Integer> userIdList;
    }
}
