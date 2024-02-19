package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Transactional
    public void confirmar(Long pedidoID) {
        var pedido = pedidoService.buscarOuFalhar(pedidoID);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO))
            throw new NegocioException(
                String.format("Status do pedido %s não pode ser alterado de %s para %s",
                    pedidoID,
                    pedido.getStatus().getDescricao(),
                    StatusPedido.CONFIRMADO.getDescricao()));

        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }
}
