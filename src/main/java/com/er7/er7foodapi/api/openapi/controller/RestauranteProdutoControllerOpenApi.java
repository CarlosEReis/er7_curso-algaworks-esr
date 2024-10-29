package com.er7.er7foodapi.api.openapi.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.model.ProdutoModel;
import com.er7.er7foodapi.api.model.input.ProdutoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation("Lista os produto de um restaurante.")
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    CollectionModel<ProdutoModel> listar(
        @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteID,
        @ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", example = "false", defaultValue = "false") Boolean incluirInativos);

    @ApiOperation("Cadastra um produto de um restaurante.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    ProdutoModel adicionar(
        @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteID,
        @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true) ProdutoInput produtoInput);

    @ApiOperation("Busca o produto de um restaurante.")
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))) })
    ProdutoModel buscar(
        @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteID,
        @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoID);

    @ApiOperation("Atualiza o produto de um restaurante.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado"),
        @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
    })
    ProdutoModel atualizar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteID,
            @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoID,
            @ApiParam(name = "corpo", value = "Representação de um produto com os novos dados",required = true) ProdutoInput produtoInput);
}
