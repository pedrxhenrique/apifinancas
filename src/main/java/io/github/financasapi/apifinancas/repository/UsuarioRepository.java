package io.github.financasapi.apifinancas.repository;

import io.github.financasapi.apifinancas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    List<Usuario> findByNome(String nome);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNomeAndEmail(String nome, String email);

    List<Usuario> findByNomeContainingIgnoreCase(String email);

}
