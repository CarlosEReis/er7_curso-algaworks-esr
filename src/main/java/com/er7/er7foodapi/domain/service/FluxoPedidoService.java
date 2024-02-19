package com.er7.er7foodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Transactional
    public void confirmar(Long pedidoID) {
        var pedido = pedidoService.buscarOuFalhar(pedidoID);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(Long pedidoID) {
        var pedido = pedidoService.buscarOuFalhar(pedidoID);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(Long pedidoID) {
        var pedido = pedidoService.buscarOuFalhar(pedidoID);
        pedido.cancelar();
    }
}
