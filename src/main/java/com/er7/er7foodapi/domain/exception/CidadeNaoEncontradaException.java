package com.er7.er7foodapi.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CidadeNaoEncontradaException(Long estadoId) {
        this(String.format("Não existe um cadastro de cidade com o código %d.", estadoId));
    }
}
