package com.er7.er7foodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {

    @NotBlank private String nome;
    @NotNull private Boolean ativo;
    @NotBlank private String descricao;
    @NotNull @PositiveOrZero private BigDecimal preco;
}
