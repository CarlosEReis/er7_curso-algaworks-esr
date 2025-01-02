package com.er7.er7foodapi.api.v2.assembler;

import com.er7.er7foodapi.api.v2.FoodLinksV2;
import com.er7.er7foodapi.api.v2.controller.CozinhaControllerV2;
import com.er7.er7foodapi.api.v2.model.CozinhaModelV2;
import com.er7.er7foodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinksV2 foodLinks;

    public CozinhaModelAssemblerV2() {
        super(CozinhaControllerV2.class, CozinhaModelV2.class);
    }

    @Override
    public CozinhaModelV2 toModel(Cozinha cozinha) {
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
