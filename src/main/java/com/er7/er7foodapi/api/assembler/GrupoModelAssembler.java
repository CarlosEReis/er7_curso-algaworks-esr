package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.FoodLinks;
import com.er7.er7foodapi.api.controller.GrupoController;
import com.er7.er7foodapi.api.model.GrupoModel;
import com.er7.er7foodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinks foodLinks;

    public GrupoModelAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }

    @Override
    public GrupoModel toModel(Grupo grupo) {
        var grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);
        grupoModel.add(foodLinks.linkToGrupos("grupos"));
        grupoModel.add(foodLinks.linkToPermissoesGrupo(grupo.getId(), "permissoes"));
        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToGrupos());
    }
}
