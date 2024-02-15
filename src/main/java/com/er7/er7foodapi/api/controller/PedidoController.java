package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.PedidoModelAssembler;
import com.er7.er7foodapi.api.model.PedidoModel;
import com.er7.er7foodapi.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired private EmissaoPedidoService pedidoService;
    @Autowired private PedidoModelAssembler pedidoModelAssembler;

    @GetMapping
    public List<PedidoModel> listar() {
        return pedidoModelAssembler.toCollectionModel(pedidoService.listar());
    }

    @GetMapping("/{pedidoID}")
    public PedidoModel buscar(@PathVariable Long pedidoID) {
        return pedidoModelAssembler.toModel(pedidoService.buscarOuFalhar(pedidoID));
    }
}
