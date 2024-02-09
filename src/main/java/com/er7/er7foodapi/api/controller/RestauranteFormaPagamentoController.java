package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.FormaPagamentoInputDisassembler;
import com.er7.er7foodapi.api.assembler.FormaPagamentoModelAssembler;
import com.er7.er7foodapi.api.model.FormaPagamentoModel;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteID}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired private CadastroRestauranteService restauranteService;
    @Autowired private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    @Autowired private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable Long restauranteID) {
        Restaurante restauranteDB = restauranteService.buscarOuFalhar(restauranteID);
        return formaPagamentoModelAssembler.toCollectionModel(restauranteDB.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteID, @PathVariable Long formaPagamentoID) {
        restauranteService.desassociarFormaPagamento(restauranteID, formaPagamentoID);
    }

    @PutMapping("/{formaPagamentoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteID, @PathVariable Long formaPagamentoID) {
        restauranteService.associarFormaPagamento(restauranteID, formaPagamentoID);
    }
}
