package io.github.financasapi.apifinancas.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException(String msg) {
        super(msg);
    }
}
