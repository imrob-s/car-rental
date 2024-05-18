package dev.imrob.carrental.service;

import dev.imrob.carrental.dto.UsuarioCreateDto;
import dev.imrob.carrental.dto.UsuarioResponseDto;
import dev.imrob.carrental.dto.UsuarioSenhaDto;
import dev.imrob.carrental.dto.mapper.UsuarioMapper;
import dev.imrob.carrental.entity.Usuario;
import dev.imrob.carrental.exceptions.EntityNotFoundException;
import dev.imrob.carrental.exceptions.LoginUniqueViolationException;
import dev.imrob.carrental.exceptions.SenhaIncorretaException;
import dev.imrob.carrental.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    @Transactional
    public UsuarioResponseDto save(UsuarioCreateDto usuarioCreateDto) {
        Usuario usuario = mapper.toEntity(usuarioCreateDto);
        try {
            usuario = repository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new LoginUniqueViolationException("Login já cadastrado");
        }
        return mapper.toResponseDto(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDto findById(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Usuário id %d não encontrado".formatted(id))
        );
        return mapper.toResponseDto(usuario);
    }

    @Transactional
    public UsuarioResponseDto editarSenha(Long id, UsuarioSenhaDto dto) {
        Usuario usuario = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado")
        );

        if (!dto.novaSenha().equals(dto.confirmarSenha())) {
            throw new SenhaIncorretaException("As senhas não conferem");
        }

        if (!usuario.getSenha().equals(dto.senhaAtual())) {
            throw new SenhaIncorretaException("Senha atual incorreta");
        }
        usuario.setSenha(dto.novaSenha());
        return mapper.toResponseDto(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> findAll() {
        return mapper.toDto(repository.findAll());
    }
}
