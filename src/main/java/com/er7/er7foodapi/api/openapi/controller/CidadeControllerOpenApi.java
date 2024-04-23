package com.er7.er7foodapi.api.openapi.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.model.CidadeModel;
import com.er7.er7foodapi.api.model.input.CidadeInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades.")
    public List<CidadeModel> listar();

    @ApiOperation("Busca uma cidade específica com base no ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "400", description = "ID da cidade é inválido", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))})
    public CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

    @ApiOperation("Cadastra uma cidade.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cidade cadastrada")})
    public CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade específica com base no ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cidade atualizada"),
        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))})
    public CidadeModel atualizar(
        @ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId,
        @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados.", required = true) CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade específica com base no ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cidade excluída"),
        @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))})
    public void remover(@ApiParam(value = "ID de uma cidade", required = true) Long cidadeId);
}
