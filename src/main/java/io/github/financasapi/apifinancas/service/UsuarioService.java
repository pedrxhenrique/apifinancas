package io.github.financasapi.apifinancas.service;

import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.repository.UsuarioRepository;
import io.github.financasapi.apifinancas.validador.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioValidator validator;

    public Usuario salvar(Usuario usuario) {
        validator.validar(usuario);
        return repository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    public List<Usuario> pesquisa(String nome){
        if(nome != null && !nome.trim().isEmpty()){
            return repository.findByNomeContainingIgnoreCase(nome);
        }
        return repository.findAll();
    }
}
