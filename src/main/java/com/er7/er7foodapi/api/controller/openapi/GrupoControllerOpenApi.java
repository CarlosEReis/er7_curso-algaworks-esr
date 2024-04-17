package com.er7.er7foodapi.api.controller.openapi;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.model.GrupoModel;
import com.er7.er7foodapi.api.model.input.GrupoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Grupo Cadastrado.")})
    public GrupoModel criar(@ApiParam(name = "corpo", value = "Respresentação de um novo grupo.") GrupoInput grupoInput);

    @ApiOperation("Lista os grupos")
    public List<GrupoModel> listar();

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Grupo atualizado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))})
    public GrupoModel atualizar(Long grupoID, GrupoInput grupoInput) ;

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "ID do grupo inválido.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))})
    public GrupoModel buscar(Long grupoID);

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Grupo excluído.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))})
    public void excluir(Long grupoID);

}