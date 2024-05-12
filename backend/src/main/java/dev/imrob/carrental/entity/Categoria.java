package dev.imrob.carrental.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "valor_diaria")
    private Double valorDiaria;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "usuario_criacao")
    private Usuario usuarioCriacao;

    @Column(name = "data_alteracao")
    private LocalDateTime dataAlteracao;

    @ManyToOne
    @JoinColumn(name = "usuario_alteracao")
    private Usuario usuarioAlteracao;
}
