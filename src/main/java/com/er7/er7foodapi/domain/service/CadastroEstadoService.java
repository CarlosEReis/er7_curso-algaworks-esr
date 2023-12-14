package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.EntidadeEmUsoException;
import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.model.Estado;
import com.er7.er7foodapi.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return this.estadoRepository.salvar(estado);
    }

    public Estado Atualizar(Long estadoId, Estado estado) {
        var estadoDB = this.estadoRepository.buscar(estadoId);
        if (estadoDB == null)
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe um cadastro de estado com o código %d.", estadoId));
        BeanUtils.copyProperties(estado, estadoDB, "id");
        return this.salvar(estadoDB);
    }

    public void remover(Long estadoId) {
        try {
            this.estadoRepository.remover(estadoId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe um cadastro de estado com o código %d.", estadoId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format("Estado de código %d não pode ser removido, pois está em uso", estadoId));
        }
    }
}
