package com.er7.er7foodapi.domain.service;

import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.exception.PedidoNaoEncontradoException;
import com.er7.er7foodapi.domain.model.Pedido;
import com.er7.er7foodapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmissaoPedidoService {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private CadastroCidadeService cidadeService;
    @Autowired private CadastroUsuarioService usuarioService;
    @Autowired private CadastroProdutoService produtoService;
    @Autowired private CadastroRestauranteService restauranteService;
    @Autowired private CadastroFormaPagamentoService formaPagamentoService;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido emitirPedido(Pedido pedido) {
        validarPedido(pedido);
        validaItens(pedido);
        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarOuFalhar(Long pedidoID) {
        return pedidoRepository.findById(pedidoID)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoID));
    }

    private void validaItens(Pedido pedido) {
        pedido.getItens().forEach(
            itemPedido -> {
                var restauranteID = pedido.getRestaurante().getId();
                var produtoID = itemPedido.getProduto().getId();
                var produto = produtoService.buscarOuFalhar(restauranteID, produtoID);
                itemPedido.setProduto(produto);
                itemPedido.setPrecoUnitario(produto.getPreco());
                itemPedido.calculaValorTotal();
                itemPedido.setPedido(pedido);
            }
        );
    }

    private void validarPedido(Pedido pedido) {
        var cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
        var cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        var restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        var formaDePagamento= formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaDePagamento);
        pedido.getEnderecoEntrega().setCidade(cidade);

        if (restaurante.estaInativo())
            throw new NegocioException(
                    String.format("O restaurante de código %s não está ativo.", restaurante.getId()));

        if (restaurante.estaFechado())
            throw new NegocioException(
                    String.format("O restaurante de código %s não está aberto no momento", restaurante.getId()));

        if (restaurante.naoAceita(formaDePagamento))
            throw new NegocioException(
                    String.format("O restaurante de código %s, não aceita a forma de pagamento de código %s", restaurante.getId(), formaDePagamento.getId()));

    }

}
