package io.github.financasapi.apifinancas.controller;

import io.github.financasapi.apifinancas.dto.TransacaoDTO;
import io.github.financasapi.apifinancas.model.Transacao;
import io.github.financasapi.apifinancas.service.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody TransacaoDTO transacao) {
        var entidade = transacao.mapearTransacao();
        transacaoService.salvar(entidade);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entidade.getId()).toUri();
        return ResponseEntity.created(location).body(entidade);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> pesquisa(@PathVariable String id) {
        var idTransacao = UUID.fromString(id);
        Optional<Transacao> transacaoOptional = transacaoService.buscarPorId(idTransacao);
        if (transacaoOptional.isPresent()) {
            Transacao transacaoEntity = transacaoOptional.get();
            TransacaoDTO dto = new TransacaoDTO(transacaoEntity.getId(), transacaoEntity.getDescricao(), transacaoEntity.getTipo(), transacaoEntity.getValor(),
                    transacaoEntity.getData(), transacaoEntity.getIdUsuario().getId(), transacaoEntity.getIdCategoria().getId());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

}
