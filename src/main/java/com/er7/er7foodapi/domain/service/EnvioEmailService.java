package com.er7.er7foodapi.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

public interface EnvioEmailService {

    void enviar(Menssagem menssagem);

    @Getter
    @Builder
    class Menssagem {

        @Singular
        private Set<String> destinatarios;

        @NonNull
        private String assunto;

        @NonNull
        private String corpo;
    }
}
