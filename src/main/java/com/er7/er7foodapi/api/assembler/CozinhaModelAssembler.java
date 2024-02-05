package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.model.CozinhaModel;
import com.er7.er7foodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CozinhaModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaModel toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaModel.class);
    }

    public List<CozinhaModel> toColletionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
            .map(this::toModel)
            .toList();
    }
}
