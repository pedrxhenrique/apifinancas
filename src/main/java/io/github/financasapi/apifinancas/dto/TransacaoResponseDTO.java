package io.github.financasapi.apifinancas.dto;

import io.github.financasapi.apifinancas.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransacaoResponseDTO(UUID ID, String descricao, String tipo, BigDecimal valor, LocalDate data, UUID idCategoria, String nomeCategoria, UUID idUsuario, String email) {

    public static TransacaoResponseDTO mapearResponseTransacao(Transacao transacao) {
        return new TransacaoResponseDTO(transacao.getId(),
                transacao.getDescricao(),
                transacao.getTipo(),
                transacao.getValor(),
                transacao.getData(),
                transacao.getIdCategoria().getId(),
                transacao.getIdCategoria().getNome(),
                transacao.getIdUsuario().getId(),
                transacao.getIdUsuario().getEmail()
        );
    }
}
