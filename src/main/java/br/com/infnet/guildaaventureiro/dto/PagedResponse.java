package br.com.infnet.guildaaventureiro.dto;

import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Range;

import java.util.List;

public record PagedResponse<T>(
        @Min(value = 0, message = "A página não pode ser negativa")
        int page,
        @Range(min = 1, max = 50, message = "O tamanho deve ser entre 1 e 50")
        int size,
        int total,
        int totalPages,
        List<T> content
) {
    public PagedResponse(int page, int size, int total, List<T> content) {
        this(
                page,
                size,
                total,
                size <= 0 ? 0 : (int) Math.ceil((double) total / size),
                content
        );
    }
}
