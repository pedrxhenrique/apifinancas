package io.github.financasapi.apifinancas.dto.errors.common;

import io.github.financasapi.apifinancas.dto.errors.ErrorCampo;
import io.github.financasapi.apifinancas.dto.errors.ErrorResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandles {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<ErrorCampo> listaErros = fieldErrors.stream().map(error -> new ErrorCampo(error.getField(), error.getDefaultMessage())).collect(Collectors.toList());
        return new ErrorResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação, provavel campo em branco", listaErros);
    }
}
