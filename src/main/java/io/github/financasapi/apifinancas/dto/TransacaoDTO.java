package io.github.financasapi.apifinancas.dto;

import io.github.financasapi.apifinancas.model.Categoria;
import io.github.financasapi.apifinancas.model.Transacao;
import io.github.financasapi.apifinancas.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransacaoDTO(UUID id, @NotBlank String descricao, @NotBlank String tipo, @NotNull BigDecimal valor, @NotNull LocalDate data, UUID idCategoria,
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
