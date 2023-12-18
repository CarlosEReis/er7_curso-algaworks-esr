package com.er7.er7foodapi.domain.repository;

import com.er7.er7foodapi.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
    List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);
    List<Restaurante> findTop2ByNomeContaining(String nome);
    Optional<Restaurante> findFirstByNomeContaining(String nome);
    int countByCozinhaId(Long cozinhaId);
}
