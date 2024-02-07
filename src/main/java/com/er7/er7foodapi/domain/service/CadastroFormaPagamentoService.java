package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.EntidadeEmUsoException;
import com.er7.er7foodapi.domain.exception.FormaPagamentoEncontradaException;
import com.er7.er7foodapi.domain.model.FormaPagamento;
import com.er7.er7foodapi.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroFormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento %d não pode ser removida, pois está em uso.";

    @Autowired
    private FormaPagamentoRepository pagamentoRepository;

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return pagamentoRepository.save(formaPagamento);
    }

    public List<FormaPagamento> listar() {
        return pagamentoRepository.findAll();
    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoID) {
        return pagamentoRepository.findById(formaPagamentoID)
            .orElseThrow(() -> new FormaPagamentoEncontradaException(formaPagamentoID));
    }

    @Transactional
    public void excluir(Long formaPagamentoID) {
        try {
            pagamentoRepository.deleteById(formaPagamentoID);
            pagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw  new FormaPagamentoEncontradaException(formaPagamentoID);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoID));
        }
    }
}
