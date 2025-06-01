package io.github.financasapi.apifinancas.service;

import io.github.financasapi.apifinancas.expections.OperacaoNaoPermitidaException;
import io.github.financasapi.apifinancas.expections.TransacaoNaoEncontradaException;
import io.github.financasapi.apifinancas.model.Categoria;
import io.github.financasapi.apifinancas.model.Transacao;
import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.repository.CategoriaRepository;
import io.github.financasapi.apifinancas.repository.TransacaoRepository;
import io.github.financasapi.apifinancas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<Transacao> buscarPorId(UUID id) {
        return transacaoRepository.findById(id);
    }

    public Transacao atualizar(UUID id, Transacao transacao) {
        Transacao transacaoExiste = transacaoRepository.findById(id).orElseThrow(() -> new OperacaoNaoPermitidaException("Transação não encontrada."));
        if (transacao.getDescricao() == null || transacao.getDescricao().isBlank()) {
            throw new OperacaoNaoPermitidaException("A descrição é obrigatória.");
        }
        transacaoExiste.setDescricao(transacao.getDescricao());
        transacaoExiste.setTipo(transacao.getTipo());
        transacaoExiste.setValor(transacao.getValor());

        return transacaoRepository.save(transacaoExiste);
    }

    public void deletar(UUID id){
        if(!transacaoRepository.existsById(id)){
            throw new TransacaoNaoEncontradaException("Transação com o ID informado não existe.");
        }
        transacaoRepository.deleteById(id);
    }

    public List<Transacao> pesquisa(String tipo){
        if(tipo != null && !tipo.trim().isEmpty()){
            return transacaoRepository.findByTipoContainingIgnoreCase(tipo);
        }
        return transacaoRepository.findAll();
    }
}

