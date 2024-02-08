package com.er7.er7foodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioComSenhaInput extends UsuarioInput{

    @NotBlank
    private String senha;
}
