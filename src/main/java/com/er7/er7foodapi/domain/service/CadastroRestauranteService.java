package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.exception.RestauranteNaoEncontradoException;
import com.er7.er7foodapi.domain.model.FormaPagamento;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired private CadastroFormaPagamentoService formaPagamentoService;
    @Autowired private RestauranteRepository restauranteRepository;
    @Autowired private CadastroCozinhaService cozinhaService;
    @Autowired private CadastroCidadeService cidadeService;
    @Autowired private CadastroUsuarioService usuarioService;

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

    @Transactional
    public void abrir(Long restauranteID) {
        var restauranteDB = buscarOuFalhar(restauranteID);
        restauranteDB.setAberto(Boolean.TRUE);
    }

    @Transactional
    public void fechar(Long restauranteID) {
        var restauranteDB = buscarOuFalhar(restauranteID);
        restauranteDB.setAberto(Boolean.FALSE);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteID, Long responsavelID) {
        var restaurante = buscarOuFalhar(restauranteID);
        var responsavel = usuarioService.buscarOuFalhar(responsavelID);
        if (restaurante.naoPossui(responsavel))
            throw new NegocioException(
                String.format("O restaurante de código %s, não possui um responsavel de código %s associado.",
                    responsavelID, responsavelID));
        restaurante.remove(responsavel);
    }

    @Transactional
    public void associarResponsavel(Long restauranteID, Long responsavelID) {
        var restaurante = buscarOuFalhar(restauranteID);
        var responsavel = usuarioService.buscarOuFalhar(responsavelID);
        restaurante.adiciona(responsavel);
    }

    @Transactional
    public void ativar(List<Long> restaurantesIDs) {
        restaurantesIDs.forEach(this::ativar);
    }

    @Transactional
    public void inativar(List<Long> restaurantesIDs) {
        restaurantesIDs.forEach(this::inativar);
    }
}
