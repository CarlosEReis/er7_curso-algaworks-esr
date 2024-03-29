package com.er7.er7foodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInput {

    @NotBlank private String cep;
    @NotBlank private String logradouro;
    @NotBlank private String numero;
    @NotBlank private String bairro;
    @Valid @NotNull private CidadeIdInput cidade;
    private String complemento;
}
