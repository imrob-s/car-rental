package dev.imrob.carrental.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String placa;

    private Integer ano;
    private String cor;
    private Integer kilometragem;

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private Boolean disponivel;

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
