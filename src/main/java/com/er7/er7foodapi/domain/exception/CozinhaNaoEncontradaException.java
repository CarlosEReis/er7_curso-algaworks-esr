package com.er7.er7foodapi.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long estadoId) {
        this(String.format("Não existe um cadastro de cozinha com o código %d.", estadoId));
    }
}
