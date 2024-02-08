package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.model.UsuarioModel;
import com.er7.er7foodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModel toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }

    public List<UsuarioModel> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream()
            .map(this::toModel)
            .toList();
    }
}
