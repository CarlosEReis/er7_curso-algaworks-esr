package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.model.PermissaoModel;
import com.er7.er7foodapi.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PermissaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public List<PermissaoModel> toColletionModel(Collection<Permissao> permissoes) {
        return permissoes.stream()
            .map(this::toModel)
            .toList();
    }
}
