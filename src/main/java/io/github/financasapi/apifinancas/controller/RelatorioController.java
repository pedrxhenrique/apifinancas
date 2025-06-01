package io.github.financasapi.apifinancas.controller;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final TransacaoService transacaoService;
    private final UsuarioService usuarioService;
    private final RelatorioService relatorioService;


    @GetMapping("/{id}")
    public ResponseEntity<?> baixarRelatorio(@PathVariable UUID id) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResposta.naoEncontrado("O usuário não existe."));
            }

            Usuario usuario = usuarioOpt.get();
            List<Transacao> transacoes = transacaoService.buscarPorIDPDF(id);

            String nome = usuario.getNome();
            String email = usuario.getEmail();
            BigDecimal limite = usuario.getLimiteMensal();

            byte[] pdfBytes = relatorioService.gerarRelatorioFinanceiro(nome, transacoes, limite, email);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            logger.error("Erro ao gerar relatório para usuário id: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResposta.naoEncontrado("Erro ao gerar relatório. Veja o log para detalhes."));
        }
    }
}

