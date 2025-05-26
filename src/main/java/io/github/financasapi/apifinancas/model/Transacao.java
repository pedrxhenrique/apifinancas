package io.github.financasapi.apifinancas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transacao")
@Data
public class Transacao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "data")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria idCategoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
}
