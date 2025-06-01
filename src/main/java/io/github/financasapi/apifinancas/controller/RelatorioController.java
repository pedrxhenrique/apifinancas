package io.github.financasapi.apifinancas.controller;

import io.github.financasapi.apifinancas.dto.errors.ErrorResposta;
import io.github.financasapi.apifinancas.model.Transacao;
import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.service.RelatorioService;
import io.github.financasapi.apifinancas.service.TransacaoService;
import io.github.financasapi.apifinancas.service.UsuarioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final TransacaoService transacaoService;
    private final UsuarioService usuarioService;
    private final RelatorioService relatorioService;

    public RelatorioController(TransacaoService transacaoService, UsuarioService usuarioService, RelatorioService relatorioService) {
        this.transacaoService = transacaoService;
        this.usuarioService = usuarioService;
        this.relatorioService = relatorioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> baixarRelatorio(@PathVariable UUID id) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("O usuário não existe."));
            }
            Usuario usuario = usuarioOpt.get();
            List<Transacao> transacoes = transacaoService.buscarPorIDPDF(id);
            byte[] pdfBytes = relatorioService.gerarRelatorioFinanceiro(usuario.getNome(), transacoes);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
