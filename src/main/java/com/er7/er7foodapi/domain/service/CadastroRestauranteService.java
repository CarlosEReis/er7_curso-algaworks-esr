package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import com.er7.er7foodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante adicionar(Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        var cozinha = this.cozinhaRepository.findById(cozinhaId)
                .orElseThrow(
                    () -> new EntidadeNaoEncontradaException(
                        String.format("Cozinha com o id %d não existe.", cozinhaId)));

        restaurante.setCozinha(cozinha);
        return this.restauranteRepository.save(restaurante);
    }
}
