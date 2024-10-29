package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.PedidoInputDisassembler;
import com.er7.er7foodapi.api.assembler.PedidoModelAssembler;
import com.er7.er7foodapi.api.assembler.PedidoResumoModelAssembler;
import com.er7.er7foodapi.api.model.PedidoModel;
import com.er7.er7foodapi.api.model.PedidoResumoModel;
import com.er7.er7foodapi.api.model.input.PedidoInput;
import com.er7.er7foodapi.api.openapi.controller.PedidoControllerOpenApi;
import com.er7.er7foodapi.core.data.PageWrapper;
import com.er7.er7foodapi.core.data.PageableTranslator;
import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.filter.PedidoFilter;
import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.model.Usuario;
import com.er7.er7foodapi.domain.repository.PedidoRepository;
import com.er7.er7foodapi.domain.service.EmissaoPedidoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired private EmissaoPedidoService pedidoService;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private PedidoModelAssembler pedidoModelAssembler;
    @Autowired private PedidoInputDisassembler pedidoInputDisassembler;
    @Autowired private PedidoResumoModelAssembler pedidoResumoModelAssembler;
    @Autowired private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

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

    @ApiImplicitParams(
        @ApiImplicitParam(name = "campos", paramType = "query", type = "string", value = "Nomes das propriedades para filtrar na resposta, separados por vírgula."))
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter pedidoFilter, Pageable pageable) {
        Pageable pageableTraduzido = traduzirPageable(pageable);
        var pedidosPage = pedidoService.listar(pedidoFilter, pageableTraduzido);
        pedidosPage = new PageWrapper<>(pedidosPage, pageable);
        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string", value = "Nomes das propriedades para filtrar na resposta, separados por vírgula")
    )
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        return pedidoModelAssembler.toModel(pedidoService.buscarOuFalhar(codigoPedido));
    }

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "nomerestaurante", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );
        return PageableTranslator.translator(pageable, mapeamento);
    }
}
