package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.FoodLinks;
import com.er7.er7foodapi.api.controller.RestauranteController;
import com.er7.er7foodapi.api.model.RestauranteApenasNomeModel;
import com.er7.er7foodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinks foodLinks;

    public RestauranteApenasNomeModelAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
        var restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        restauranteModel.add(foodLinks.linkToRestaurantes("restaurantes"));
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToRestaurantes());
    }
}
