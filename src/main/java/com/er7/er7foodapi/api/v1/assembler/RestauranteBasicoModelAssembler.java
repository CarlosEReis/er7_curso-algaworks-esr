package com.er7.er7foodapi.api.v1.assembler;

import com.er7.er7foodapi.api.v1.FoodLinks;
import com.er7.er7foodapi.api.v1.controller.RestauranteController;
import com.er7.er7foodapi.api.v1.model.RestauranteBasicoModel;
import com.er7.er7foodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinks foodLinks;

    public RestauranteBasicoModelAssembler() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }

    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        var restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        restauranteModel.add(foodLinks.linkToRestaurantes("restaurantes"));
        restauranteModel.getCozinha().add(foodLinks.linkToCozinha(restaurante.getCozinha().getId()));
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToRestaurantes());
    }
}
