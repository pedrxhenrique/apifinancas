package io.github.financasapi.apifinancas.expections;

public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException(String msg) {
        super(msg);
    }
}
