package com.er7.er7foodapi.api.v1.openapi.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.v1.model.PedidoModel;
import com.er7.er7foodapi.api.v1.model.PedidoResumoModel;
import com.er7.er7foodapi.api.v1.model.input.PedidoInput;
import com.er7.er7foodapi.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation("Registra um novo pedido.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido registrado") })
    PedidoModel criar(@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true) PedidoInput pedidoInput);

    @ApiOperation("Pesquisa um pedido.")
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
            name = "campos", paramType = "query", type = "string")})
    PagedModel<PedidoResumoModel> pesquisar(PedidoFilter pedidoFilter, Pageable pageable);

    @ApiOperation("Busca um pedido pelo código.")
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
            name = "campos", paramType = "query", type = "string") })
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)) )})
    PedidoModel buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);
}
