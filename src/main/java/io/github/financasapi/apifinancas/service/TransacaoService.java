package io.github.financasapi.apifinancas.service;

import io.github.financasapi.apifinancas.model.Categoria;
import io.github.financasapi.apifinancas.model.Transacao;
import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.repository.CategoriaRepository;
import io.github.financasapi.apifinancas.repository.TransacaoRepository;
import io.github.financasapi.apifinancas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public Transacao salvar(Transacao transacao) {
        UUID idTransacao = transacao.getIdUsuario().getId();
        Usuario usuario = usuarioRepository.findById(idTransacao).orElseThrow(() -> new RuntimeException("O usuário não existe"));

        UUID idCat = transacao.getIdCategoria().getId();
        Categoria categoria = categoriaRepository.findById(idCat).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        transacao.setIdUsuario(usuario);
        transacao.setIdCategoria(categoria);
        return transacaoRepository.save(transacao);

    }
}
