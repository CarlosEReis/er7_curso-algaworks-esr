package com.er7.er7foodapi.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long grupoID) {
        this(String.format("Não existe um cadastro de grupo com o código %d.", grupoID));
    }
}
