package com.er7.er7foodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends RuntimeException{

    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }

}
