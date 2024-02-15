package com.er7.er7foodapi.api.assembler;

import com.er7.er7foodapi.api.model.PedidoResumoModel;
import com.er7.er7foodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoResumoModelAssembler {

    @Autowired private ModelMapper modelMapper;

    public PedidoResumoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoModel.class);
    }

    public List<PedidoResumoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
            .map(this::toModel)
            .toList();
    }
}
