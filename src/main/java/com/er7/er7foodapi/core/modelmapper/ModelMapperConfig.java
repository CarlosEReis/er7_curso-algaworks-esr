package com.er7.er7foodapi.core.modelmapper;

import com.er7.er7foodapi.api.model.EnderecoModel;
import com.er7.er7foodapi.api.model.input.ItemPedidoInput;
import com.er7.er7foodapi.domain.model.Endereco;
import com.er7.er7foodapi.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        //mapper.createTypeMap(Restaurante.class, RestauranteModel.class).addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
            .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        var enderecoToEnderecoModeltypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
        enderecoToEnderecoModeltypeMap.<String>addMapping(
                endereco -> endereco.getCidade().getEstado().getNome(),
                (enderecoModel, value) -> enderecoModel.getCidade().setEstado(value)
        );

        return modelMapper;
    }
}
