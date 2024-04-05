package com.er7.er7foodapi.domain.listenter;

import com.er7.er7foodapi.domain.event.PedidoConfirmadoEvent;
import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.service.EnvioEmailService;
import com.er7.er7foodapi.domain.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired private EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();
        Mensagem menssagem = Mensagem.builder()
            .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
            .corpo("pedido-confirmado.html")
            .variavel("pedido", pedido)
            .build();
        envioEmail.enviar(menssagem);
    }
}
