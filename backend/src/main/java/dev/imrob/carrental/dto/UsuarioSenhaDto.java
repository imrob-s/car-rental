package dev.imrob.carrental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioSenhaDto(
        @NotBlank
        @Size(min = 6, max = 12)
        String senhaAtual,
        @NotBlank
        @Size(min = 6, max = 12)
        String novaSenha,
        @NotBlank
        @Size(min = 6, max = 12)
        String confirmarSenha
) {
}
