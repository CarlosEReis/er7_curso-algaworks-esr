package com.er7.er7foodapi.domain.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO);

    @Getter
    private String descricao;
    private List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
        return !novoStatus.statusAnteriores.contains(this);
    }

    public boolean podeSerAlteradoPara(StatusPedido status) {
        return !naoPodeAlterarPara(status);
    }
}
