package io.github.financasapi.apifinancas.dto;

import io.github.financasapi.apifinancas.model.Usuario;

import java.math.BigDecimal;
import java.util.UUID;

public record UsuarioResponseDTO(UUID id, String nome, String email, BigDecimal limiteMensal) {

    public static UsuarioResponseDTO fromUsuario(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLimiteMensal()
        );
    }
}