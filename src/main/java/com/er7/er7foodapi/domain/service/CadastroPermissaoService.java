package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.PermissaoNaoEncontradaException;
import com.er7.er7foodapi.domain.model.Permissao;
import com.er7.er7foodapi.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscaOuFalha(Long permissaoID) {
        return permissaoRepository.findById(permissaoID)
            .orElseThrow(
                () -> new PermissaoNaoEncontradaException(permissaoID));
    }

}
