package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired private EmissaoPedidoService pedidoService;
    @Autowired private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(String codigoPedido) {
        var pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        var pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.cancelar();
        pedidoRepository.save(pedido);
    }
}
