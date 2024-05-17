package dev.imrob.carrental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCreateDto(
        @NotBlank
        @Email(message = "Email inválido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        String login,
        @NotBlank @Size(min = 6, max = 12)
        String senha
) {
}
