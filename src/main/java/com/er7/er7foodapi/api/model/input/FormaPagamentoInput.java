package com.er7.er7foodapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaPagamentoInput {

    @ApiModelProperty(example = "Bitcoin", required = true)
    @NotBlank
    private String descricao;
}
