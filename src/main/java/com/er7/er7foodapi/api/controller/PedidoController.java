package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.PedidoInputDisassembler;
import com.er7.er7foodapi.api.assembler.PedidoModelAssembler;
import com.er7.er7foodapi.api.assembler.PedidoResumoModelAssembler;
import com.er7.er7foodapi.api.model.PedidoModel;
import com.er7.er7foodapi.api.model.PedidoResumoModel;
import com.er7.er7foodapi.api.model.input.PedidoInput;
import com.er7.er7foodapi.core.data.PageableTranslator;
import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.filter.VendaDiariaFilter;
import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.model.Usuario;
import com.er7.er7foodapi.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//        List<Pedido> pedidos = pedidoService.listar();
//        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//        SimpleFilterProvider filtersProvider = new SimpleFilterProvider();
//
//        if (StringUtils.isBlank(campos))
//            filtersProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//        else
//            filtersProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//
//        pedidosWrapper.setFilters(filtersProvider);
//        return pedidosWrapper;
//    }

    @GetMapping
    public Page<PedidoResumoModel> pesquisar(VendaDiariaFilter.PedidoFilter pedidoFilter, Pageable pageable) {

        pageable = traduzirPageable(pageable);

        Page<Pedido> pagePedidos = pedidoService.listar(pedidoFilter, pageable);
        List<PedidoResumoModel> listPedidosModel = pedidoResumoModelAssembler.toCollectionModel(pagePedidos.getContent());
        return new PageImpl<>(listPedidosModel, pageable, pagePedidos.getTotalElements());
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        return pedidoModelAssembler.toModel(pedidoService.buscarOuFalhar(codigoPedido));
    }

    private Pageable traduzirPageable(Pageable pageable) {
        Map<String, String> mapeamento =
            Map.of(
                "codigo", "codigo",
                "restaurante", "restaurante",
                "nomeCliente", "cliente.nome",
                "subTotal", "subTotal",
                "valorTotal", "valorTotal"
        );
        return PageableTranslator.translator(pageable, mapeamento);
    }
}
