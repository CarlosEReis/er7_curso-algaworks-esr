package com.er7.er7foodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada."),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso."),
    ERRO_NEGOCIO("/erro-negocio", "Violação da regra de negócio.");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://er7food.com.br/".concat(path);
        this.title = title;
    }
}
