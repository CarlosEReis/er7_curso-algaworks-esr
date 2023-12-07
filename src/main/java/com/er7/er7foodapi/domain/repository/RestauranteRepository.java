package com.er7.er7foodapi.domain.repository;

import com.er7.er7foodapi.domain.model.Restaurante;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listar();
    Restaurante buscar(Long id);
    Restaurante salvar(Restaurante restaurante);
    void remover(Restaurante restaurante);
}
