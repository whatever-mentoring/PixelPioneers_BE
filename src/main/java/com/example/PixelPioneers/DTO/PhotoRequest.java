package com.example.PixelPioneers.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class PhotoRequest {
    @Getter
    @Setter
    public static class PhotoAddDTO {
        @NotEmpty
        private String name;

        @NotNull
        private int peopleCount;

        @NotEmpty
        private LocalDate created_at;

        @NotNull
        private boolean open;
    }

    @Getter
    @Setter
    public static class PhotoIsOpenUpdateDTO {
        @NotNull
        private boolean open;
    }
}