package com.er7.er7foodapi.domain.exception;

public class FormaPagamentoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoEncontradaException(Long formaPagamentoID) {
        this(String.format("Não existe uma forma de pagamento com o código %d.", formaPagamentoID));
    }
}
