package io.github.financasapi.apifinancas.validador;

import io.github.financasapi.apifinancas.expections.RegistroDuplicadoExpection;
import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioValidator {

    private UsuarioRepository usuarioRepository;

    public UsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validar(Usuario usuario) {
        if(existeUsuarioCadastrado(usuario)) {
            throw new RegistroDuplicadoExpection("O usuário já está cadastrado em nossa base, tente outro email " + usuario.getEmail());
        }
    }

    private boolean existeUsuarioCadastrado(Usuario usuario) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioEncontrado.isEmpty()) {
            return false;
        }
        if (usuario.getId() == null) {
            return true;
        }
        return !usuario.getId().equals(usuarioEncontrado.get().getId());
    }
}
