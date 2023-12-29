package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.EntidadeEmUsoException;
import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe um cadastro de cozinha com o código %d";
    public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return this.cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId) {
        try {
            this.cozinhaRepository.deleteById(cozinhaId);
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public Cozinha buscaOuFalha(Long cozinhaId) {
        return this.cozinhaRepository.findById(cozinhaId)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId)));
    }
}
