package dev.imrob.carrental.controller;

import dev.imrob.carrental.entity.Usuario;
import dev.imrob.carrental.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public ResponseEntity<UsuarioDTO> salvar(Usuario usuario) {
        Usuario usuarioSalvo = service.salvar(usuario);
    }

}
