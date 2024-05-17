package dev.imrob.carrental.dto;

public record UsuarioResponseDto(
        Long id,
        String login,
        String tipoUsuario
) {
}
