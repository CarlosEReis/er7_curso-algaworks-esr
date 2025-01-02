package com.er7.er7foodapi.api.v2.assembler;

import com.er7.er7foodapi.api.v2.model.CidadeModelV2;
import com.er7.er7foodapi.api.v2.FoodLinksV2;
import com.er7.er7foodapi.api.v2.controller.CidadeControllerV2;
import com.er7.er7foodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinksV2 foodLinks;

    public CidadeModelAssemblerV2() {
        super(CidadeControllerV2.class, CidadeModelV2.class);
    }

    @Override
    public CidadeModelV2 toModel(Cidade cidade) {
        var cidadeModel = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModel);
        cidadeModel.add(foodLinks.linkToCidades("cidades"));
        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToCidades());
    }

}
