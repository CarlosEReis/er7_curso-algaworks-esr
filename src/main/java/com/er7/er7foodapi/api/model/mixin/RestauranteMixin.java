package com.er7.er7foodapi.api.model.mixin;

import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.model.Endereco;
import com.er7.er7foodapi.domain.model.FormaPagamento;
import com.er7.er7foodapi.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

public abstract class RestauranteMixin {

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private LocalDateTime dataAtualizacao;

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento;

    @JsonIgnore
    private List<Produto> produtos;
}