package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired private EmissaoPedidoService pedidoService;
    @Autowired private EnvioEmailService envioEmailService;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = pedidoService.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        Mensagem menssagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();
        envioEmailService.enviar(menssagem);
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
    }
}
