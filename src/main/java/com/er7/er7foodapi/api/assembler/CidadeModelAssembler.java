package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.FoodLinks;
import com.er7.er7foodapi.api.controller.CidadeController;
import com.er7.er7foodapi.api.model.CidadeModel;
import com.er7.er7foodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinks foodLinks;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        var cidadeModel = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModel);
        cidadeModel.add(foodLinks.linkToCidades("cidades"));
        cidadeModel.getEstado().add(foodLinks.linkToEstado(cidade.getEstado().getId()));
        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToCidades());
    }

}
