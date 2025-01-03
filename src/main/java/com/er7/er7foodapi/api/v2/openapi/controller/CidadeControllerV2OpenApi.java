package com.er7.er7foodapi.api.v2.openapi.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.v2.model.CidadeModelV2;
import com.er7.er7foodapi.api.v2.model.input.CidadeInputV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerV2OpenApi {

    @ApiOperation("Lista as cidades.")
    CollectionModel<CidadeModelV2> listar();

    @ApiOperation("Busca uma cidade específica com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "400", description = "ID da cidade é inválido", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))})
    CidadeModelV2 buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

    @ApiOperation("Cadastra uma cidade.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cidade cadastrada")})
    CidadeModelV2 adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInputV2 cidadeInput);

    @ApiOperation("Atualiza uma cidade específica com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cidade atualizada"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))})
    CidadeModelV2 atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId,
            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados.", required = true) CidadeInputV2 cidadeInput);

    @ApiOperation("Exclui uma cidade específica com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cidade excluída"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))})
    void remover(@ApiParam(value = "ID de uma cidade", required = true) Long cidadeId);
}
