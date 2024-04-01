package com.er7.er7foodapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

public interface EnvioEmailService {

    void enviar(Menssagem menssagem);

    @Getter
    @Builder
    class Menssagem {

        private Set<String> destinatarios;
        private String assunto;
        private String corpo;
    }
}
