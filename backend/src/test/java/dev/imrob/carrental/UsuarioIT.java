package dev.imrob.carrental;

import dev.imrob.carrental.dto.UsuarioCreateDto;
import dev.imrob.carrental.dto.UsuarioResponseDto;
import dev.imrob.carrental.dto.UsuarioSenhaDto;
import dev.imrob.carrental.exceptions.handler.CustomError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {
    @Autowired
    WebTestClient testClient;

    @Test
    public void criarUsuario_ComLoginESenhaValidos_RetornaUsuarioCriadoComStatus201() {
        UsuarioResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("joao@email.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.id()).isNotNull();
        assertThat(responseBody.login()).isEqualTo("joao@email.com");
        assertThat(responseBody.tipoUsuario()).isEqualTo("CLIENTE");
    }

    @Test
    public void criarUsuario_ComLoginInvalido_RetornaCustomErrorStatus422() {
        CustomError responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("joao@", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("joao@email", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void criarUsuario_ComSenhaInvalida_RetornaCustomErrorStatus422() {
        CustomError responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("joao@email.com", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("joao@email.com", "12345678901234"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("joao@email.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void criarUsuario_ComLoginRepetido_RetornaCustomErrorStatus409() {
        CustomError responseBody = testClient
                .post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("ana@email.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void buscarPeloId_ComIdExistente_RetornaUsuarioComStatus200() {
        UsuarioResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.id()).isEqualTo(100);
        assertThat(responseBody.login()).isEqualTo("ana@email.com");
        assertThat(responseBody.tipoUsuario()).isEqualTo("ADMINISTRADOR");
    }

    @Test
    public void buscarPeloId_ComIdInexistente_RetornaCustomErrorComStatus404() {
        CustomError responseBody = testClient
                .get()
                .uri("/api/v1/usuarios/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void editarSenha_ComDadosValidos_RetornaStatus204() {
        testClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "101010","101010"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void editarSenha_ComIdInexistente_RetornaCustomErrorComStatus404() {
        CustomError responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "101010","101010"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void editarSenha_ComCamposInvalidos_RetornaCustomErrorComStatus422() {
        CustomError responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("", "",""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123","123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "1234567890123","1234567890123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void editarSenha_ComSenhaInvalidas_RetornaCustomErrorComStatus400() {
        CustomError responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "101010","111111"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("654321", "101010","101010"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(CustomError.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(400);
    }
}
