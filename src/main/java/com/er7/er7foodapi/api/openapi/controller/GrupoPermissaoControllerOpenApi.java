package com.er7.er7foodapi.api.openapi.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.model.PermissaoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões associadas a um grupo")
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class))) })
    List<PermissaoModel> listar(
        @ApiParam(value = "ID do grupo", example = "1", required = true)
        Long grupoId);

    @ApiOperation("Desassociação de permissão com grupo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada", content = @Content(schema = @Schema(implementation = Problem.class))) })
    void desassociar(
        @ApiParam(value = "ID do grupo", example = "1", required = true)
        Long grupoId,

        @ApiParam(value = "ID da permissão", example = "1", required = true)
        Long permissaoId);

    @ApiOperation("Associação de permissão com grupo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrada", content = @Content(schema = @Schema(implementation = Problem.class))) })
    void associar(
        @ApiParam(value = "ID do grupo", example = "1", required = true)
        Long grupoId,

        @ApiParam(value = "ID da permissão", example = "1", required = true)
        Long permissaoId);
}
