package com.er7.er7foodapi.domain.listenter;

import com.er7.er7foodapi.domain.event.PedidoCanceladoEvent;
import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.service.EnvioEmailService;
import com.er7.er7foodapi.domain.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired private EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();
        Mensagem mensagem = Mensagem
            .builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
                .corpo("pedido-cancelado.html")
                .destinatario(pedido.getCliente().getEmail())
                .variavel("pedido", pedido)
            .build();
        System.out.println("\n\nENVIANDO NOTIFICAÇÃO DE CANCELAMENTO DE PEDIDO PARA O CLIENTE.\n\n");
        envioEmail.enviar(mensagem);
    }
}
