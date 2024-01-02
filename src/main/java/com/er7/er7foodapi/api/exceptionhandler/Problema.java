package com.er7.er7foodapi.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Problema {

    private LocalDateTime datahora;
    private String mensagem;
}
