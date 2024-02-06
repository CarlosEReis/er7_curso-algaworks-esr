package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.RestauranteNaoEncontradoException;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Transactional
    public Restaurante adicionar(Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        var cozinha = this.cozinhaService.buscaOuFalha(cozinhaId);
        restaurante.setCozinha(cozinha);
        return this.restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteID) {
        Restaurante restaurante = buscarOuFalhar(restauranteID);
        restaurante.ativar();
    }
    @Transactional
    public void inativar(Long restauranteID) {
        Restaurante restaurante = buscarOuFalhar(restauranteID);
        restaurante.inativar();
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return this.restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}
