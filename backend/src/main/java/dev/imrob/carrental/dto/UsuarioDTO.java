package dev.imrob.carrental.dto;

import dev.imrob.carrental.entity.enums.Regra;

import java.time.LocalDateTime;

public record UsuarioDTO(
        Long id,
        String login,
        String senha,
        Regra tipoUsuario,
        LocalDateTime dataCriacao,
        LocalDateTime dataAlteracao,
        String criadoPor,
        String alteradoPor
) {
}
