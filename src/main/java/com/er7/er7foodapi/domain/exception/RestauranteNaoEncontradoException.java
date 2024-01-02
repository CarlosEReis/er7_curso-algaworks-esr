package com.er7.er7foodapi.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long estadoId) {
        this(String.format("Não existe um cadastro de restaurante com código %d.", estadoId));
    }
}
