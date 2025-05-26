package io.github.financasapi.apifinancas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;
}
