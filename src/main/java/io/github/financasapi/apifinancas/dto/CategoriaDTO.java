package io.github.financasapi.apifinancas.dto;

import io.github.financasapi.apifinancas.model.Categoria;
import io.github.financasapi.apifinancas.model.Usuario;

import java.util.UUID;

public record CategoriaDTO(UUID id, String nome, String descricao, UUID idUsuario) {

    public Categoria mapearCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome(nome);
        categoria.setDescricao(descricao);

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        categoria.setUsuario(usuario);

        return categoria;
    }
}
