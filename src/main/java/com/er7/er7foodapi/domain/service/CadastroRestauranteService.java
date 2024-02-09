package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.exception.RestauranteNaoEncontradoException;
import com.er7.er7foodapi.domain.model.FormaPagamento;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {

    @Autowired private CadastroFormaPagamentoService formaPagamentoService;
    @Autowired private RestauranteRepository restauranteRepository;
    @Autowired private CadastroCozinhaService cozinhaService;
    @Autowired private CadastroCidadeService cidadeService;

    @Transactional
    public Restaurante adicionar(Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        var cidadeID = restaurante.getEndereco().getCidade().getId();

        var cozinha = cozinhaService.buscaOuFalha(cozinhaId);
        var cidade = cidadeService.buscarOuFalhar(cidadeID);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
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

    @Transactional
    public void desassociarFormaPagamento(Long restauranteID, Long formaPagamentoID) {
        Restaurante restauranteDB = buscarOuFalhar(restauranteID);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoID);

        if (restauranteDB.getFormasPagamento().contains(formaPagamento)) {
            restauranteDB.removerFormaPagamento(formaPagamento);
        } else {
            throw new NegocioException(
                String.format(
                    "Não foi possível desassocionar. Restaurante de código %s, não possuir forma de pagamento de código %s associada.",
                    restauranteID, formaPagamentoID));
        }
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteID, Long formaPagamentoID) {
        Restaurante restauranteDB = buscarOuFalhar(restauranteID);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoID);
        restauranteDB.adicionarFormaPagamento(formaPagamento);
    }
}
