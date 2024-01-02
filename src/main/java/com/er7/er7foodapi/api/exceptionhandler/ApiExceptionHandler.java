package com.er7.er7foodapi.api.exceptionhandler;

import com.er7.er7foodapi.domain.exception.EntidadeEmUsoException;
import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> trataEntidadeEmUsoException(EntidadeEmUsoException e) {
        var problema = Problema.builder()
                .datahora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> trataEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
        var problema = Problema.builder()
                .datahora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> trataEntidadeNaoEncontradaException(NegocioException e) {
        var problema = Problema.builder()
                .datahora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(problema);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> trataHttpMediaTypeNotSupportedException() {
        var problema = Problema.builder()
                .datahora(LocalDateTime.now())
                .mensagem("O tipo de media não é suportado.")
                .build();
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problema);
    }
}
