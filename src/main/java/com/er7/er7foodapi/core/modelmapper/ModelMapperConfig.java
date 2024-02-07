package com.er7.er7foodapi.core.modelmapper;

import com.er7.er7foodapi.api.model.EnderecoModel;
import com.er7.er7foodapi.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        //mapper.createTypeMap(Restaurante.class, RestauranteModel.class).addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        var enderecoToEnderecoModeltypeMap = mapper.createTypeMap(Endereco.class, EnderecoModel.class);
        enderecoToEnderecoModeltypeMap.<String>addMapping(
                endereco -> endereco.getCidade().getEstado().getNome(),
                (enderecoModel, value) -> enderecoModel.getCidade().setEstado(value)
        );

        return mapper;
    }
}
