package io.github.financasapi.apifinancas.dto.errors;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResposta(int status, String mensagem, List<ErrorCampo> erros) {

    public static ErrorResposta respostaPadrao(String mensagem) {
        return new ErrorResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErrorResposta conflito(String mensagem) {
        return new ErrorResposta(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }

    public static ErrorResposta usuarioInexistente(String mensagem) {
        return new ErrorResposta(HttpStatus.NOT_FOUND.value(), mensagem, List.of());
    }

}
