package io.github.financasapi.apifinancas.service;

import io.github.financasapi.apifinancas.expections.OperacaoNaoPermitidaException;
import io.github.financasapi.apifinancas.expections.UsuarioNaoEncontradoException;
import io.github.financasapi.apifinancas.model.Categoria;
import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.repository.UsuarioRepository;
import io.github.financasapi.apifinancas.validador.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioValidator validator;
    private final UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
        validator.validarUser(usuario);
        return repository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    public List<Usuario> pesquisa(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            return repository.findByNomeContainingIgnoreCase(nome);
        }
        return repository.findAll();
    }

    public void deletar(UUID id) {
        if(!repository.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Usuário com o ID informado não existe.");
        }
        repository.deleteById(id);
    }

    public Usuario atualizar(UUID id, Usuario usuario) {
        Usuario usuarioExiste = usuarioRepository.findById(id).orElseThrow(() -> new OperacaoNaoPermitidaException("Usuário não encontrado."));
        usuarioExiste.setNome(usuario.getNome());
        usuarioExiste.setEmail(usuario.getEmail());
        usuarioExiste.setSenha(usuario.getSenha());
        return usuarioRepository.save(usuarioExiste);
    }

}
