package io.github.financasapi.apifinancas.dto;

import io.github.financasapi.apifinancas.model.Categoria;
import io.github.financasapi.apifinancas.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



import java.util.UUID;

public record CategoriaDTO(UUID id, @NotBlank(message = "O nome é obrigatório") String nome, @NotBlank(message = "A descrição é obrigatória") String descricao, @NotNull(message = "O id do usuário é obrigatório") UUID idUsuario) {

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
