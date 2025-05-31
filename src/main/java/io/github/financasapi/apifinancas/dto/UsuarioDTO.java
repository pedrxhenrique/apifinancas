package io.github.financasapi.apifinancas.dto;

import io.github.financasapi.apifinancas.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UsuarioDTO(UUID id, @NotBlank(message = "O campo é obrigatório") String nome, @NotBlank(message ="O campo é obrigatório") String email,@NotBlank(message = "O campo é obrigatório") String senha) {

    public Usuario mapearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
