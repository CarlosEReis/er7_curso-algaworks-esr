package com.er7.er7foodapi.api.v1.assembler;

import com.er7.er7foodapi.api.v1.FoodLinks;
import com.er7.er7foodapi.api.v1.controller.RestauranteProdutoFotoController;
import com.er7.er7foodapi.api.v1.model.FotoProdutoModel;
import com.er7.er7foodapi.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinks foodLinks;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto fotoProduto) {
        var fotoProdutoModel = modelMapper.map(fotoProduto, FotoProdutoModel.class);
        fotoProdutoModel.add(foodLinks.linkToProdutoFoto(fotoProduto.getRestauranteID(), fotoProduto.getId()));
        fotoProdutoModel.add(foodLinks.linkToProduto(fotoProduto.getRestauranteID(), fotoProduto.getProduto().getId(),"produto"));
        return fotoProdutoModel;
    }

}
