package com.er7.er7foodapi.api.v1.controller;

import com.er7.er7foodapi.api.v1.FoodLinks;
import com.er7.er7foodapi.api.v1.assembler.ProdutoInputDisassembler;
import com.er7.er7foodapi.api.v1.assembler.ProdutoModelAssembler;
import com.er7.er7foodapi.api.v1.model.ProdutoModel;
import com.er7.er7foodapi.api.v1.model.input.ProdutoInput;
import com.er7.er7foodapi.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.er7.er7foodapi.domain.model.Produto;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.repository.ProdutoRepository;
import com.er7.er7foodapi.domain.service.CadastroProdutoService;
import com.er7.er7foodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteID}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private CadastroProdutoService cadastroProduto;
    @Autowired private ProdutoModelAssembler produtoModelAssembler;
    @Autowired private CadastroRestauranteService cadastroRestaurante;
    @Autowired private ProdutoInputDisassembler produtoInputDisassembler;
    @Autowired private FoodLinks foodLinks;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteID,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteID);
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        produto = cadastroProduto.salvar(produto);
        return produtoModelAssembler.toModel(produto);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteID, @RequestParam(required = false) Boolean incluirInativos) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteID);

        List<Produto> todosProdutos = null;

        if (incluirInativos)
            todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        else
         todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);

        return produtoModelAssembler
            .toCollectionModel(todosProdutos)
            .add(foodLinks.linkToProdutos(restauranteID));
    }

    @GetMapping(path = "/{produtoID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel buscar(@PathVariable Long restauranteID, @PathVariable Long produtoID) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteID, produtoID);
        return produtoModelAssembler.toModel(produto);
    }

    @PutMapping(path = "/{produtoID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel atualizar(@PathVariable Long restauranteID, @PathVariable Long produtoID,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteID, produtoID);
        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
        produtoAtual = cadastroProduto.salvar(produtoAtual);
        return produtoModelAssembler.toModel(produtoAtual);
    }

}
