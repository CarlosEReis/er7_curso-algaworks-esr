package com.er7.er7foodapi.api.v1.openapi.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problem;
import com.er7.er7foodapi.api.v1.model.FormaPagamentoModel;
import com.er7.er7foodapi.api.v1.model.input.FormaPagamentoInput;
import com.er7.er7foodapi.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Formas de pagamento")
public interface FormasDePagamentoControllerOpenApi {

    @ApiOperation(value = "Cadastra uma forma de pagamento")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))})
    FormaPagamentoModel adicionar(@ApiParam(name = "corpo", value = "Representação do modelo de entrada de uma nova forma de pagamento.", required = true) FormaPagamentoInput formaPagamentoInput);

    @ApiOperation(value = "Lista as formas de pagamento")
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = FormasPagamentoModelOpenApi.class)
    })
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<FormaPagamentoModel> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagtoID, ServletWebRequest webRequest);

    @ApiOperation("Atualiza uma forma de pagamento por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))})
    FormaPagamentoModel atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagtoID, @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados", required = true) FormaPagamentoInput formaPagamentoInput);

    @ApiOperation("Excluí uma forma de pagamento por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
        @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Problem.class)))})
    void remover(@ApiParam(value = "ID de uma forma de pagamento.", example = "1", required = true) Long formaPagtoID);
}
