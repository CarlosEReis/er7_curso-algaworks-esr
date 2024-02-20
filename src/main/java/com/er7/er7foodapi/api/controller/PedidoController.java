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
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
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
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
        List<Pedido> pedidos = pedidoService.listar();
        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);

        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
        SimpleFilterProvider filtersProvider = new SimpleFilterProvider();

        if (StringUtils.isBlank(campos))
            filtersProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
        else
            filtersProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));

        pedidosWrapper.setFilters(filtersProvider);
        return pedidosWrapper;
    }

//    @GetMapping
//    public List<PedidoResumoModel> listar() {
//        return pedidoResumoModelAssembler.toCollectionModel(pedidoService.listar());
//    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        return pedidoModelAssembler.toModel(pedidoService.buscarOuFalhar(codigoPedido));
    }
}
