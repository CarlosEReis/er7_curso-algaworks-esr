package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoID}")
public class FluxoPedidoController {

    @Autowired private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable Long pedidoID) {
        fluxoPedidoService.confirmar(pedidoID);
    }
}
