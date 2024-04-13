package com.er7.er7foodapi.domain.repository;

import com.er7.er7foodapi.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("select max(data_atualizacao) from forma_pagamento")
    OffsetDateTime getDataUltimaAtualizacao();
}
