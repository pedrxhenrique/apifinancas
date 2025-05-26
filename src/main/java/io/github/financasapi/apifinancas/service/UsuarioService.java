package io.github.financasapi.apifinancas.service;

import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(UUID id) {
        return repository.findById(id);
    }
}
