package com.er7.er7foodapi.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long usuarioID) {
        this(String.format("Não existe um cadastro de usuario com o código %d.", usuarioID));
    }
}
