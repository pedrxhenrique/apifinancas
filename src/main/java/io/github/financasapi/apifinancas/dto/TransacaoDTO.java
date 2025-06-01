package io.github.financasapi.apifinancas.dto;

import io.github.financasapi.apifinancas.model.Categoria;
import io.github.financasapi.apifinancas.model.Transacao;
import io.github.financasapi.apifinancas.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransacaoDTO(UUID id, @NotBlank(message = "A descrição é obrigatória") String descricao, @NotBlank(message = "O tipo é obrigatório") String tipo, @NotNull(message = "O valor é obrigatório") BigDecimal valor, @NotNull(message = "A data é obrigatória") LocalDate data, UUID idCategoria,
                           UUID idUsuario) {

    public Transacao mapearTransacao() {
        Transacao transacao = new Transacao();
        transacao.setId(id);
        transacao.setDescricao(descricao);
        transacao.setValor(valor);
        transacao.setData(data);
        transacao.setTipo(tipo);

        Categoria categoria = new Categoria();
        categoria.setId(idCategoria);
        transacao.setIdCategoria(categoria);

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        transacao.setIdUsuario(usuario);

        return transacao;

    }
}
