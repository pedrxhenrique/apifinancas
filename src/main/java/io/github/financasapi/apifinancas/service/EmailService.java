package io.github.financasapi.apifinancas.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarAlertaComRelatorio(String to, String nomeUsuario, byte[] pdfBytes, double total, double limite) throws MessagingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("⚠️ Alerta: Você ultrapassou seu limite de gastos");
        helper.setText("""
            Olá %s,

            Seu total de gastos neste mês foi de R$ %.2f.
            Isso ultrapassa seu limite configurado de R$ %.2f.

            Em anexo, segue o relatório detalhado.
            
            Atenciosamente,
            Sistema de Finanças
        """.formatted(nomeUsuario, total, limite));

        helper.addAttachment("relatorio-financeiro.pdf", new ByteArrayResource(pdfBytes));

        mailSender.send(message);
}
}
