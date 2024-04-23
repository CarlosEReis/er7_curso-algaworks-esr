package com.er7.er7foodapi.api.openapi.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.model.RestauranteModel;
import com.er7.er7foodapi.api.model.input.RestauranteInput;
import com.er7.er7foodapi.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nome da projeção de pedidos", name = "projecao", paramType = "query", type = "string", allowableValues = "apenas-nome")} )
    public List<RestauranteModel> listar();

    @ApiOperation(value = "Lista restaurantes", hidden = true)
    public List<RestauranteModel> listarApenasNomes();

    @ApiOperation("Buscar em restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "ID do restaurante inválido.",  content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))})
    public RestauranteModel buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation(value = "Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado") })
    public RestauranteModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestauranteInput restauranteInput);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurante atualizado."),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))})
    public RestauranteModel atualizar(@ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados",
            required = true)Long restauranteId, RestauranteInput restauranteInput);

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    public void ativar(Long restauranteID);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    public void inativar(@PathVariable Long restauranteID);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    public void abrir(@PathVariable Long restauranteID);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    public void fechar(@PathVariable Long restauranteID);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso") })
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIDs);

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso") })
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIDs);
}
