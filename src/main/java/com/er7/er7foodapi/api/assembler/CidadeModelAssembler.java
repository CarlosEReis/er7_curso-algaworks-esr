package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.model.CidadeModel;
import com.er7.er7foodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadeModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeModel toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModel.class);
    }

    public List<CidadeModel> toColletionModel(List<Cidade> cidades) {
        return cidades.stream()
            .map(this::toModel)
            .toList();
    }
}
