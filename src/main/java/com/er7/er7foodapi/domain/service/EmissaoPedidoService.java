package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.PedidoNaoEncontradoException;
import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmissaoPedidoService {

    @Autowired private PedidoRepository pedidoRepository;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarOuFalhar(Long pedidoID) {
        return pedidoRepository.findById(pedidoID)
            .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoID));
    }
}
