package com.er7.er7foodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoModel {

    @ApiModelProperty(example = "2")
    private Long id;

    @ApiModelProperty(example = "Cartão de débito")
    private String descricao;
}
