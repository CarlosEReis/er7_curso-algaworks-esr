package com.er7.er7foodapi.domain.repository;

import com.er7.er7foodapi.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("select max(dataAtualizacao) from FormaPagamento")
    OffsetDateTime getDataUltimaAtualizacao();

    @Query("select max(dataAtualizacao) from FormaPagamento where id = :formaPagamentoId")
    OffsetDateTime getDataUltimaAtualizacaoById(Long formaPagamentoId);
}
