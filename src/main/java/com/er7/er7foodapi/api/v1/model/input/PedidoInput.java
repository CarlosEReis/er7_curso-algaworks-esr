package com.er7.er7foodapi.api.v1.model.input;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Data
public class PedidoInput {

    @Valid @NotNull private RestauranteIdInput restaurante;
    @Valid @NotNull private FormaPagamentoIdInput formaPagamento;
    @Valid @NotNull private EnderecoInput enderecoEntrega;
    @Valid @NotNull @Size(min = 1) private List<ItemPedidoInput> itens;
}


