package com.er7.er7foodapi.api.v1.assembler;

import com.er7.er7foodapi.api.v1.FoodLinks;
import com.er7.er7foodapi.api.v1.controller.CozinhaController;
import com.er7.er7foodapi.api.v1.model.CozinhaModel;
import com.er7.er7foodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinks foodLinks;

    public CozinhaModelAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        var cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);
        cozinhaModel.add(foodLinks.linkToCozinhas("cozinhas"));
        return cozinhaModel;
    }

    /*public List<CozinhaModel> toColletionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
            .map(this::toModel)
            .toList();
    }*/
}
