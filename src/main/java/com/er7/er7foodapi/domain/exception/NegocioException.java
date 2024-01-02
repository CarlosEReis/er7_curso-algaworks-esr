package com.er7.er7foodapi.domain.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String mensagem) {
        super(mensagem);
    }

    public NegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
