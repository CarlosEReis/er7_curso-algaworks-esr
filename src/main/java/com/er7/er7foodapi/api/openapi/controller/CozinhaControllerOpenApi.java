package com.er7.er7foodapi.api.openapi.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.model.CozinhaModel;
import com.er7.er7foodapi.api.model.input.CozinhaInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas com páginação.")
    public Page<CozinhaModel> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "Cozinha não encontrada TESTE.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "400", description = "ID da cozinha inválido.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    public CozinhaModel buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);

    @ApiOperation("Cadastra uma cozinha.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cozinha cadastrada.") })
    public CozinhaModel adicionar(@ApiParam(value = "Representação do modelo de entrada de uma nova cozinha.", required = true) CozinhaInput cozinhaInput);

    @ApiOperation("Atualiza uma cozinha por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cozinha Atualizada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Cozinha não encontrada.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    public CozinhaModel atualizar(
        @ApiParam(value = "ID de uma cozinha", example = "1",required = true) Long cozinhaId,
        @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", required = true) CozinhaInput cozinhaInput);

    @ApiOperation("Exclui uma cozinha por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cozinha excluída", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Cozinha não encontrada.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    public void remover(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);
}
