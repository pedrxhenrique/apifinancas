package io.github.financasapi.apifinancas.dto;

import io.github.financasapi.apifinancas.model.Usuario;

import java.util.UUID;

public record UsuarioDTO(UUID id, String nome, String email, String senha) {

    public Usuario mapearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
