package com.example.PixelPioneers.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class PhotoRequest {
    @Getter
    @Setter
    public static class PhotoAddDTO {
        @NotNull
        private String name;

        @NotNull
        private String image;

        @NotNull
        private int peopleCount;

        @NotNull
        @DateTimeFormat(pattern = "yyyy/MM/dd")
        private LocalDate created_at;

        @NotNull
        private boolean open;
    }
}