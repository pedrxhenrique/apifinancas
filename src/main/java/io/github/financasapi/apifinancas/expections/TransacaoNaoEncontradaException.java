package io.github.financasapi.apifinancas.expections;

public class TransacaoNaoEncontradaException extends RuntimeException {
    public TransacaoNaoEncontradaException(String message) {
        super(message);
    }
}
