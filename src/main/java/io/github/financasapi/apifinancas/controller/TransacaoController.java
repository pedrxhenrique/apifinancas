package io.github.financasapi.apifinancas.controller;

import io.github.financasapi.apifinancas.dto.TransacaoDTO;
import io.github.financasapi.apifinancas.model.Transacao;
import io.github.financasapi.apifinancas.service.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

}
