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

    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com o código %d.";
    public static final String MSG_COZINHA_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return this.estadoRepository.save(estado);
    }

    /*public Estado Atualizar(Long estadoId, Estado estado) {
        var estadoDB = this.estadoRepository.findById(estadoId)
            .orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                    String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)));

        BeanUtils.copyProperties(estado, estadoDB, "id");
        return this.salvar(estadoDB);
    }*/

    public void remover(Long estadoId) {
        try {
            this.estadoRepository.deleteById(estadoId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_COZINHA_EM_USO, estadoId));
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return this.estadoRepository.findById(estadoId)
            .orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                    String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)
            ));
    }
}
