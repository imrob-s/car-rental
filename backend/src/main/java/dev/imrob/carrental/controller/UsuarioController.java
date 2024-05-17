package dev.imrob.carrental.controller;

import dev.imrob.carrental.dto.UsuarioCreateDto;
import dev.imrob.carrental.dto.UsuarioResponseDto;
import dev.imrob.carrental.dto.UsuarioSenhaDto;
import dev.imrob.carrental.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criar(@Valid @RequestBody UsuarioCreateDto usuario) {
        UsuarioResponseDto user = service.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> atualizarSenha(@PathVariable("id") Long id, @Valid @RequestBody UsuarioSenhaDto usuarioSenhaDto) {
        service.editarSenha(id, usuarioSenhaDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
