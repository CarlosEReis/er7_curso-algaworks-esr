package com.er7.er7foodapi.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String codigoPedido) {
        super(String.format("Não existe um pedido com o código %s.", codigoPedido));
    }
}
