package io.github.financasapi.apifinancas.dto.errors;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResposta(int status, String mensagem, List<ErrorCampo> erros) {

    public static ErrorResposta respostaPadrao(String mensagem) {
        return new ErrorResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErrorResposta conflito(String mensagem) {
        return new ErrorResposta(403 , mensagem, List.of());
    }

    public static ErrorResposta naoEncontrado(String mensagem) {
        return new ErrorResposta(404, mensagem, List.of());
    }

}
