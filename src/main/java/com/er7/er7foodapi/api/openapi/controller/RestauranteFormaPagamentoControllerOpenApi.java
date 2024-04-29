package com.er7.er7foodapi.api.openapi.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.model.FormaPagamentoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;


@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))) })
    public List<FormaPagamentoModel> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteID);

    @ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))) })
    public void desassociar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteID, @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoID);

    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))) })
    public void associar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteID, @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoID);
}
