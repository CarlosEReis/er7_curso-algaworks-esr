package com.er7.er7foodapi.domain.service;

import lombok.*;

import java.util.Map;
import java.util.Set;

public interface EnvioEmailService {

    void enviar(Mensagem menssagem);

    @Getter
    @Builder
    class Mensagem {

        @Setter
        @Singular
        private Set<String> destinatarios;

        @NonNull
        private String assunto;

        @NonNull
        private String corpo;

        @Singular("variavel")
        private Map<String, Object> variaveis;
    }
}
