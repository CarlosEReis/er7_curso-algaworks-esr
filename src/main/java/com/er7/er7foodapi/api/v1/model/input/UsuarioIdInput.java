package com.er7.er7foodapi.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UsuarioIdInput {

    @NotNull private Long id;
}
