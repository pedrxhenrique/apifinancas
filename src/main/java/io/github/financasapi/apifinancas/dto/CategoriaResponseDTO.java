package io.github.financasapi.apifinancas.dto;

import io.github.financasapi.apifinancas.model.Categoria;

import java.util.UUID;

public record CategoriaResponseDTO(UUID id, String nome, String descricao, UUID idUsuario) {

    public CategoriaResponseDTO mapearResponseCategoria(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao(),
                categoria.getUsuario().getId()
                );
    }

}
