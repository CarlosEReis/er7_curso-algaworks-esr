package com.er7.er7foodapi.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long permissaoID) {
        this(String.format("Não existe um cadastro de permissao com o código %d.", permissaoID));
    }
}
