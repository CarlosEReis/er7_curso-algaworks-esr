package com.er7.er7foodapi.api.v1.controller;

import com.er7.er7foodapi.api.v1.FoodLinks;
import com.er7.er7foodapi.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.er7.er7foodapi.api.v1.assembler.FormaPagamentoModelAssembler;
import com.er7.er7foodapi.api.v1.model.FormaPagamentoModel;
import com.er7.er7foodapi.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteID}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired private CadastroRestauranteService restauranteService;
    @Autowired private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    @Autowired private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
    @Autowired private FoodLinks foodLinks;

    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteID) {
        Restaurante restauranteDB = restauranteService.buscarOuFalhar(restauranteID);
        var formasPagamentoModel = formaPagamentoModelAssembler.toCollectionModel(restauranteDB.getFormasPagamento())
            .removeLinks()
            .add(foodLinks.linkToRestauranteFormasPagamento(restauranteID))
            .add(foodLinks.linkToRestauranteFormaPagamentoAssociar(restauranteID, "associar"));

        formasPagamentoModel.getContent().forEach(
            formaPagamento -> formaPagamento.add(
                foodLinks.linkToRestauranteFormaPagamentoDesassociar(restauranteID, formaPagamento.getId(), "desassociar")));
        return formasPagamentoModel;
    }

    @DeleteMapping("/{formaPagamentoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteID, @PathVariable Long formaPagamentoID) {
        restauranteService.desassociarFormaPagamento(restauranteID, formaPagamentoID);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formaPagamentoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteID, @PathVariable Long formaPagamentoID) {
        restauranteService.associarFormaPagamento(restauranteID, formaPagamentoID);
        return ResponseEntity.noContent().build();
    }
}
