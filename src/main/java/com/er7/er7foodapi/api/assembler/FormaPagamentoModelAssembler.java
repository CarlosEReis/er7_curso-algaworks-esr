package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.model.FormaPagamentoModel;
import com.er7.er7foodapi.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FormaPagamentoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> toCollectionModel(List<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()
            .map(this::toModel)
            .toList();
    }

}
