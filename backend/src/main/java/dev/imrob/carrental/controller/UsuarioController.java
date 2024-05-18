package dev.imrob.carrental.controller;

import dev.imrob.carrental.dto.UsuarioCreateDto;
import dev.imrob.carrental.dto.UsuarioResponseDto;
import dev.imrob.carrental.dto.UsuarioSenhaDto;
import dev.imrob.carrental.exceptions.handler.CustomError;
import dev.imrob.carrental.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de usuários.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    @Operation(summary = "Cria um novo usuário.", description = "Recurso para criar um novo usuário.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuario e-mail já cadastrado no sistema.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomError.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Dados inválidos.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomError.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criar(@Valid @RequestBody UsuarioCreateDto usuario) {
        UsuarioResponseDto user = service.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "Recuperar um usuario pelo id", description = "Recuperar um usuario pelo id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso não encontrado.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomError.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPeloId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Atualizar Senha", description = "Atualizar Senha",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Senha não confere.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomError.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso não encontrado.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomError.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Campos inválidos ou mal formatados.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomError.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> atualizarSenha(@PathVariable("id") Long id, @Valid @RequestBody UsuarioSenhaDto usuarioSenhaDto) {
        service.editarSenha(id, usuarioSenhaDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obter todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDto.class)))
            })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

}
