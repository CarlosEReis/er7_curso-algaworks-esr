package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import com.er7.er7foodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(@RequestParam String nome) {
        return this.cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/unico-por-nome")
    public Optional<Cozinha> cozinhaPorNome(@RequestParam String nome) {
        return this.cozinhaRepository.findByNome(nome);
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return this.restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantePorNomeAndCozinha(String nome, Long cozinhaId) {
        return this.restauranteRepository.findByNomeContainingAndCozinhaId(nome, cozinhaId);
    }
}
