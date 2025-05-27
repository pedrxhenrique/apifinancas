package io.github.financasapi.apifinancas.repository;

import io.github.financasapi.apifinancas.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

    List<Categoria> findByNome(String nome);

}
