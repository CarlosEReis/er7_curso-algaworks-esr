package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.model.EstadoModel;
import com.er7.er7foodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstadoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoModel toModel(Estado estado) {
        return modelMapper.map(estado, EstadoModel.class);
    }

    public List<EstadoModel> toColletionModel(List<Estado> estados) {
        return estados.stream()
            .map(this::toModel)
            .toList();
    }
}
