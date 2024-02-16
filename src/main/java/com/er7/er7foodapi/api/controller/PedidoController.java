package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.PedidoInputDisassembler;
import com.er7.er7foodapi.api.assembler.PedidoModelAssembler;
import com.er7.er7foodapi.api.assembler.PedidoResumoModelAssembler;
import com.er7.er7foodapi.api.model.PedidoModel;
import com.er7.er7foodapi.api.model.PedidoResumoModel;
import com.er7.er7foodapi.api.model.input.PedidoInput;
import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.model.Usuario;
import com.er7.er7foodapi.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired private EmissaoPedidoService pedidoService;
    @Autowired private PedidoModelAssembler pedidoModelAssembler;
    @Autowired private PedidoInputDisassembler pedidoInputDisassembler;
    @Autowired private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel criar(@RequestBody @Valid PedidoInput pedidoInput) {

        try {
            Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            // TODO: pegar usuário autenticado
            var cliente = new Usuario();
            cliente.setId(1L);

            pedido.setCliente(cliente);
            return pedidoModelAssembler.toModel(pedidoService.emitirPedido(pedido));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @GetMapping
    public List<PedidoResumoModel> listar() {
        return pedidoResumoModelAssembler.toCollectionModel(pedidoService.listar());
    }

    @GetMapping("/{pedidoID}")
    public PedidoModel buscar(@PathVariable Long pedidoID) {
        return pedidoModelAssembler.toModel(pedidoService.buscarOuFalhar(pedidoID));
    }
}
