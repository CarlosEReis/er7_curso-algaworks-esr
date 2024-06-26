package com.er7.er7foodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoModel {

    @ApiModelProperty(value = "ID do estado", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome do estado", example = "Minas Gerais")
    private String nome;
}
