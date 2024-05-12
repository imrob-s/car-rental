package dev.imrob.carrental.dto;

import dev.imrob.carrental.entity.Endereco;

public record ClienteDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    String telefone,
    Endereco endereco,
    Boolean reservaAtiva
){}
