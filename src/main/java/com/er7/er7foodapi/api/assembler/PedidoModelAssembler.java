package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.model.PedidoModel;
import com.er7.er7foodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoModelAssembler {

    @Autowired private ModelMapper modelMapper;

    public PedidoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
            .map(this::toModel)
            .toList();
    }
}
