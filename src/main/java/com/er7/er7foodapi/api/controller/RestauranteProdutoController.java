package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.ProdutoInputDisassembler;
import com.er7.er7foodapi.api.assembler.ProdutoModelAssembler;
import com.er7.er7foodapi.api.model.ProdutoModel;
import com.er7.er7foodapi.api.model.input.ProdutoInput;
import com.er7.er7foodapi.domain.model.Produto;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.repository.ProdutoRepository;
import com.er7.er7foodapi.domain.service.CadastroProdutoService;
import com.er7.er7foodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteID}/produtos")
public class RestauranteProdutoController {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private CadastroProdutoService cadastroProduto;
    @Autowired private ProdutoModelAssembler produtoModelAssembler;
    @Autowired private CadastroRestauranteService cadastroRestaurante;
    @Autowired private ProdutoInputDisassembler produtoInputDisassembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteID,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteID);
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        produto = cadastroProduto.salvar(produto);
        return produtoModelAssembler.toModel(produto);
    }

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteID) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteID);
        List<Produto> todosProdutos = produtoRepository.findByRestaurante(restaurante);
        return produtoModelAssembler.toCollectionModel(todosProdutos);
    }

    @GetMapping("/{produtoID}")
    public ProdutoModel buscar(@PathVariable Long restauranteID, @PathVariable Long produtoID) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteID, produtoID);
        return produtoModelAssembler.toModel(produto);
    }

    @PutMapping("/{produtoID}")
    public ProdutoModel atualizar(@PathVariable Long restauranteID, @PathVariable Long produtoID,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteID, produtoID);
        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
        produtoAtual = cadastroProduto.salvar(produtoAtual);
        return produtoModelAssembler.toModel(produtoAtual);
    }

}
