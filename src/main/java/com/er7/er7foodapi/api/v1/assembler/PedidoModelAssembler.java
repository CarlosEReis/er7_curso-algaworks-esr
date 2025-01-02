package com.er7.er7foodapi.api.v1.assembler;

import com.er7.er7foodapi.api.v1.FoodLinks;
import com.er7.er7foodapi.api.v1.controller.PedidoController;
import com.er7.er7foodapi.api.v1.model.PedidoModel;
import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.model.StatusPedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired private ModelMapper modelMapper;
    @Autowired private FoodLinks foodLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        var pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(foodLinks.linkToPedidos("pedidos"));
        pedidoModel.getRestaurante().add(foodLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        pedidoModel.getCliente().add(foodLinks.linkToUsuario(pedido.getCliente().getId()));
        pedidoModel.getFormaPagamento().add(foodLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        pedidoModel.getEnderecoEntrega().getCidade().add(foodLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        pedidoModel.getItens().forEach(item ->
            item.add(
                foodLinks.linkToProduto(pedido.getRestaurante().getId(), item.getProdutoId(), "produto")));

        if (pedido.getStatus().podeSerAlteradoPara(StatusPedido.CONFIRMADO))
            pedidoModel.add(foodLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));

        if (pedido.getStatus().podeSerAlteradoPara(StatusPedido.ENTREGUE))
            pedidoModel.add(foodLinks.linkToEntregaPedido(pedidoModel.getCodigo(), "entregar"));

        if (pedido.getStatus().podeSerAlteradoPara(StatusPedido.CANCELADO))
            pedidoModel.add(foodLinks.linkToCancelamento(pedido.getCodigo(), "cancelar"));

        return pedidoModel;
    }

    public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
            .map(this::toModel)
            .toList();
    }
}
