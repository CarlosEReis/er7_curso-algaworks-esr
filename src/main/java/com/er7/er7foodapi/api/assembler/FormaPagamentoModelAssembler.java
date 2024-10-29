package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.FoodLinks;
import com.er7.er7foodapi.api.controller.FormaDePagamentoController;
import com.er7.er7foodapi.api.model.FormaPagamentoModel;
import com.er7.er7foodapi.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoModelAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinks foodLinks;

    public FormaPagamentoModelAssembler() {
        super(FormaDePagamentoController.class, FormaPagamentoModel.class);
    }

    @Override
    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        var formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);
        modelMapper.map(formaPagamento, formaPagamentoModel);
        formaPagamentoModel.add(foodLinks.linkToFormasPagamento("formasPagamento"));
        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities).add(foodLinks.linkToFormasPagamento("formasPagamento"));
    }
}
