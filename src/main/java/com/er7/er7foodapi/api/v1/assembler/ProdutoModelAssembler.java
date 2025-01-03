package com.er7.er7foodapi.api.v1.assembler;

import com.er7.er7foodapi.api.v1.FoodLinks;
import com.er7.er7foodapi.api.v1.controller.RestauranteProdutoController;
import com.er7.er7foodapi.api.v1.model.ProdutoModel;
import com.er7.er7foodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinks foodLinks;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        var produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, produtoModel);
        produtoModel.add(foodLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        produtoModel.add(foodLinks.linkToProdutoFoto(produto.getRestaurante().getId(), produto.getId(), "foto"));
        return produtoModel;
    }
}
