package com.ampuero.msvc.resenia.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class ReseniaUpdateDTO {
    private Integer rating;

    private String comentario;
}

