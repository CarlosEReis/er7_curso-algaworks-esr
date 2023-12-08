package com.er7.er7foodapi.domain.repository;

import com.er7.er7foodapi.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> buscar();
    Estado buscar(Long id);
    Estado salvar(Estado estado);
    void remover(Estado estado);
}
