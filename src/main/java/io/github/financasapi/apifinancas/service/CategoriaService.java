package io.github.financasapi.apifinancas.service;

import io.github.financasapi.apifinancas.expections.CategoriaNaoEncontradaException;
import io.github.financasapi.apifinancas.expections.OperacaoNaoPermitidaException;
import io.github.financasapi.apifinancas.model.Categoria;
import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.repository.CategoriaRepository;
import io.github.financasapi.apifinancas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public Categoria salvar(Categoria categoria) {
        UUID usuarioID = categoria.getUsuario().getId();
        Usuario usuario = usuarioRepository.findById(usuarioID).orElseThrow(() -> new RuntimeException("O usuário não existe"));
        categoria.setUsuario(usuario);
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> buscarPorId(UUID id) {
        return categoriaRepository.findById(id);
    }

    public List<Categoria> pesquisa(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            return categoriaRepository.findByNomeContainingIgnoreCase(nome);
        }
        return categoriaRepository.findAll();
    }

    public Categoria atualizar(UUID id, Categoria categoria) {
        Categoria categoriaExiste = categoriaRepository.findById(id).orElseThrow(() -> new OperacaoNaoPermitidaException("Categoria não encontrada."));
        categoriaExiste.setNome(categoria.getNome());
        categoriaExiste.setDescricao(categoria.getDescricao());
        return categoriaRepository.save(categoriaExiste);
    }

    public void deletar(UUID id) {
        if(!categoriaRepository.existsById(id)) {
            throw new CategoriaNaoEncontradaException("Categoria com o ID informado não existe");
        }
        categoriaRepository.deleteById(id);
    }
}
