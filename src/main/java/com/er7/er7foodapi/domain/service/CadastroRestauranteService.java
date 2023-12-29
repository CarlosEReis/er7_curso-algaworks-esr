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

    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe um cadastro de restaurante com código %d";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    public Restaurante adicionar(Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        var cozinha = this.cozinhaService.buscaOuFalha(cozinhaId);
        restaurante.setCozinha(cozinha);
        return this.restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return this.restauranteRepository.findById(restauranteId)
            .orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                    String.format(MSG_RESTAURANTE_NAO_ENCONTRADO + ".", restauranteId)));
    }
}
