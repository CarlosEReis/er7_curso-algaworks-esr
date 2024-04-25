package com.er7.er7foodapi.api.openapi.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.model.UsuarioModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    @ApiOperation(value = "Lista os usuários responsáveis associados a um restaurante.")
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    public List<UsuarioModel> listarResponsaveis(
        @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteID);

    @ApiOperation(value = "Desassociação de um restaurante com usuário responsável.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    public void desassociaResponsavel(
        @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteID,
        @ApiParam(value = "Id do usuário", example = "1", required = true) Long usuarioID);

    @ApiOperation(value = "Associação de um restaurante com um usuário responsável.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    public void associoarResponsavel(
        @ApiParam(value = "ID do restaurante") Long restauranteID,
        @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioID);
}
