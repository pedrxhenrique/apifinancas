package io.github.financasapi.apifinancas.expections;

public class RegistroDuplicadoExpection extends RuntimeException {

    public RegistroDuplicadoExpection(String message) {
        super(message);
    }
}
