package com.example.PixelPioneers.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class PhotoRequest {
    @Getter
    @Setter
    public static class PhotoAddDTO {
        @NotBlank
        private String name;

        @NotBlank
        private int peopleCount;

        @NotBlank
        private LocalDate created_at;

        @NotBlank
        private boolean open;
    }

    @Getter
    @Setter
    public static class PhotoIsOpenUpdateDTO {
        @NotBlank
        private boolean open;
    }
}