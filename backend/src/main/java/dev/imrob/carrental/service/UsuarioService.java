package dev.imrob.carrental.service;

import dev.imrob.carrental.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {

    }
}
