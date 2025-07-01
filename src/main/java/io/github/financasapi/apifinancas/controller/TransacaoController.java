package io.github.financasapi.apifinancas.controller;

import io.github.financasapi.apifinancas.dto.CategoriaResponseDTO;
import io.github.financasapi.apifinancas.dto.TransacaoDTO;
import io.github.financasapi.apifinancas.dto.TransacaoResponseDTO;
import io.github.financasapi.apifinancas.dto.errors.ErrorResposta;
import io.github.financasapi.apifinancas.exceptions.OperacaoNaoPermitidaException;
import io.github.financasapi.apifinancas.model.Transacao;
import io.github.financasapi.apifinancas.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Object> salvarTransacao(@RequestBody @Valid TransacaoDTO transacao) {
        Transacao t1 = transacaoService.salvar(transacao.mapearTransacao());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(t1.getId()).toUri();
        return ResponseEntity.created(location).body(TransacaoResponseDTO.mapearResponseTransacao(t1));

    }

    @GetMapping("{id}")
    public ResponseEntity<Object> pesquisaTransacao(@PathVariable String id) {
        var idTransacao = UUID.fromString(id);
        Optional<Transacao> transacaoOptional = transacaoService.buscarPorId(idTransacao);
        if (transacaoOptional.isPresent()) {
            Transacao transacao = transacaoOptional.get();
            TransacaoResponseDTO transacaoDTO = new TransacaoResponseDTO(transacao.getId(), transacao.getDescricao(), transacao.getTipo(),
                    transacao.getValor(), transacao.getData(), transacao.getIdCategoria().getId(), transacao.getIdCategoria().getNome(), transacao.getIdUsuario().getId(), transacao.getIdUsuario().getEmail());
            return ResponseEntity.ok(transacaoDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("A transação informada não foi encontrada."));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarTransacao(@PathVariable("id") String id, @RequestBody @Valid TransacaoDTO transacao) {
        var idTransacao = UUID.fromString(id);
        Optional<Transacao> transacaoOptional = transacaoService.buscarPorId(idTransacao);
        if (transacaoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var entityTransacao = transacaoOptional.get();
        entityTransacao.setDescricao(transacao.descricao());
        entityTransacao.setTipo(transacao.tipo());
        entityTransacao.setValor(transacao.valor());

        transacaoService.atualizar(idTransacao, entityTransacao);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removerTransacao(@PathVariable("id") String id) {
        try {
            var idTransacao = UUID.fromString(id);
            Optional<Transacao> transacao = transacaoService.buscarPorId(idTransacao);
            if (transacao.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("O id informado não existe."));
            }
            transacaoService.deletar(idTransacao);
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException ex) {
            var errorResposta = ErrorResposta.naoEncontrado(ex.getMessage());
            return ResponseEntity.status(errorResposta.status()).body(errorResposta);
        }
    }

    @GetMapping
    public ResponseEntity<?> pesquisaDetalhadaTransacao(@RequestParam(value = "tipo", required = false) String tipo){
        List<Transacao> lista = transacaoService.pesquisa(tipo);
        if(lista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("A transação não foi encontrada em nossa base."));
        }
        List<TransacaoResponseDTO> listaDTO = lista.stream().map(transacao -> new TransacaoResponseDTO(transacao.getId(),transacao.getDescricao(), transacao.getTipo(),
                transacao.getValor(), transacao.getData(), transacao.getIdCategoria().getId(), transacao.getIdCategoria().getNome(), transacao.getIdUsuario().getId(), transacao.getIdUsuario().getEmail())).collect(Collectors.toList());
            return ResponseEntity.ok(listaDTO);
    }

}


