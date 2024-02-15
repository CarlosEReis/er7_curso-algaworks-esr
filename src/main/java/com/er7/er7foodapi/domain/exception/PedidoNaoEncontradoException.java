package com.er7.er7foodapi.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(Long pedidoID) {
        this(String.format("Não existe um pedido com o código %d.", pedidoID));
    }
}
