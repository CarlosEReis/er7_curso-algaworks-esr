package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.CozinhaNaoEncontradaException;
import com.er7.er7foodapi.domain.exception.EntidadeEmUsoException;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {

    public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return this.cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void excluir(Long cozinhaId) {
        try {
            this.cozinhaRepository.deleteById(cozinhaId);
        } catch (EmptyResultDataAccessException e){
            throw new CozinhaNaoEncontradaException(cozinhaId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public Cozinha buscaOuFalha(Long cozinhaId) {
        return this.cozinhaRepository.findById(cozinhaId)
            .orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
    }
}
