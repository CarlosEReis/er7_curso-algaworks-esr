package com.er7.er7foodapi.domain.repository;

import com.er7.er7foodapi.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();
    Cidade buscar(Long id);
    Cidade salvar(Cidade cidade);
    void remover(Long cidadeId);
}
