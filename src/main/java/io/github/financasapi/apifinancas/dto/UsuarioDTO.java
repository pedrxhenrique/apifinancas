package io.github.financasapi.apifinancas.dto;

import io.github.financasapi.apifinancas.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UsuarioDTO(UUID id, @NotBlank(message = "O nome é obrigatório") String nome, @NotBlank(message ="O email é obrigatório") String email,@NotBlank(message = "A senha é obrigatória") String senha) {

    public Usuario mapearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
