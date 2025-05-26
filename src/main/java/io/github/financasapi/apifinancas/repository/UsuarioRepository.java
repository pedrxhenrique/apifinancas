package io.github.financasapi.apifinancas.repository;

import io.github.financasapi.apifinancas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    List<Usuario> findByNome(String nome);
}
