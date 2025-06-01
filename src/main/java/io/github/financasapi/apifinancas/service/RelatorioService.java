package io.github.financasapi.apifinancas.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import io.github.financasapi.apifinancas.model.Transacao;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import java.io.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final EmailService emailService;

    public byte[] gerarRelatorioFinanceiro(String nomeUsuario, List<Transacao> transacoes, BigDecimal limite, String email) throws DocumentException, MessagingException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();
        document.add(new Paragraph("Relatório Financeiro de " + nomeUsuario));
        document.add(new Paragraph(" "));

        BigDecimal totalMes = BigDecimal.ZERO;

        for (Transacao t : transacoes) {
            StringBuilder sb = new StringBuilder();

            sb.append("Descrição: ").append(t.getDescricao()).append("\n");
            sb.append("Tipo: ").append(t.getTipo()).append("\n");
            sb.append("Valor: R$ ").append(t.getValor()).append("\n");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            sb.append("Data: ").append(t.getData().format(formatter)).append("\n");

            if (t.getIdCategoria() != null) {
                sb.append("Categoria: ").append(t.getIdCategoria().getNome());
                if (t.getIdCategoria().getDescricao() != null) {
                    sb.append(" (").append(t.getIdCategoria().getDescricao()).append(")");
                }
                sb.append("\n");
            }

            sb.append("--------------------------------------------------");

            document.add(new Paragraph(sb.toString()));
            document.add(new Paragraph(" "));

            if (t.getValor() != null) {
                totalMes = totalMes.add(t.getValor());
            }
        }

        document.add(new Paragraph(" "));
        document.add(new Paragraph("TOTAL DO MÊS: R$ " + totalMes));
        document.close();

        byte[] pdf = outputStream.toByteArray();

        if (limite != null && totalMes.compareTo(limite) > 0) {
            emailService.enviarAlertaComRelatorio(
                    email, nomeUsuario, pdf,
                    totalMes.doubleValue(), limite.doubleValue()
            );
        }
        return pdf;
    }
}
