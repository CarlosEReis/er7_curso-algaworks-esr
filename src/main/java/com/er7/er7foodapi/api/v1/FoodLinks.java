package com.er7.er7foodapi.api.v1;

import com.er7.er7foodapi.api.v1.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.TemplateVariable.VariableType;

@Component
public class FoodLinks {

    private static final TemplateVariables PAGINACAO_VARIABLES  = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM));

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", VariableType.REQUEST_PARAM));

    public Link linkToPedidos(String relation) {
        var filtroVariables = new TemplateVariables(
            new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
            new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
            new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
            new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), relation);
    }

    public Link linkToConfirmacaoPedido(String codigo, String relation) {
        return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigo)).withRel(relation);
    }

    public Link linkToEntregaPedido(String codigo, String relation) {
        return linkTo(methodOn(FluxoPedidoController.class).entregar(codigo)).withRel(relation);
    }

    public Link linkToCancelamento(String codigo, String relation) {
        return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigo)).withRel(relation);
    }

    public Link linkToRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteId)).withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantes(String relation) {
        String restauranteUri = linkTo(RestauranteController.class).toUri().toString();
        return Link.of(UriTemplate.of(restauranteUri, PROJECAO_VARIABLES), relation);
    }

    public Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteAbertura(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteController.class).abrir(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteFechamento(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteController.class).fechar(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteAtivacao(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteController.class).ativar(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteInativacao(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteController.class).inativar(restauranteId)).withRel(relation);
    }

    public Link linkToUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioId)).withRel(rel);
    }

    public Link linkToUsuario(Long usuarioId) {
        return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(UsuarioController.class).withRel(rel);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToGruposUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioId)).withRel(rel);
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupos(String relation) {
        return linkTo(GrupoController.class).withRel(relation);
    }

    public Link linkToGrupos() {
        return linkToGrupos("grupos");
    }

    public Link linkToPermissoesGrupo(Long grupoId, String relation) {
        return linkTo(methodOn(GrupoPermissaoController.class).listar(grupoId)).withRel(relation);
    }

    public Link linkToPermissoes() {
        return linkTo(PermissaoController.class).withRel(IanaLinkRelations.SELF);
    }

    public Link linkToPermissoes(String relation) {
        return linkTo(PermissaoController.class).withRel(relation);
    }

    public Link linkToAssociarPermissao(Long grupoId){
        return linkTo(methodOn(GrupoPermissaoController.class).associar(grupoId, null)).withRel("associar");
    }

    public Link linkToDesassociarPermissao(Long grupoId, Long permissaoId){
        return linkTo(methodOn(GrupoPermissaoController.class).desassociar(grupoId, permissaoId)).withRel("desassociar");
    }

    public Link linkToAssociarGrupo(Long usuarioId) {
        return linkTo(methodOn(UsuarioGrupoController.class).associar(usuarioId, null)).withRel("associar");
    }

    public Link linktoDesassociarGrupo(Long usuarioId, Long grupoId) {
        return linkTo(methodOn(UsuarioGrupoController.class).desassociar(usuarioId, grupoId)).withRel("dessaciociar");
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteResponsaveisController.class)
                .listarResponsaveis(restauranteId)).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToResponsaveisRestauranteAssociar(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteResponsaveisController.class).associoarResponsavel(restauranteId, null)).withRel(relation);
    }

    public Link linkToResponsaveisRestauranteDesassociar(Long restauranteId, Long usuarioId,String relation) {
        return linkTo(methodOn(RestauranteResponsaveisController.class).associoarResponsavel(restauranteId, usuarioId)).withRel(relation);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
        return linkTo(methodOn(FormaDePagamentoController.class)
                .buscar(formaPagamentoId, null)).withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormasPagamento(String relation) {
        return linkTo(FormaDePagamentoController.class).withRel(relation);
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormaPagamento(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(restauranteId)).withRel(relation);
    }

    public Link linkToRestauranteFormaPagamentoDesassociar(Long restauranteId, Long formaPagamentoId, String relation) {
      return linkTo(methodOn(RestauranteFormaPagamentoController.class).desassociar(restauranteId, formaPagamentoId)).withRel(relation);
    }

    public Link linkToRestauranteFormaPagamentoAssociar(Long restauranteId, String relation) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).associar(restauranteId, null)).withRel(relation);
    }

    public Link linkToProdutos(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .listar(restauranteId, null)).withRel(rel);
    }

    public Link linkToProdutos(Long restauranteId) {
        return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProdutoFoto(Long restauranteId, Long produtoId, String relation) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class).recuperarFoto(restauranteId, produtoId)).withRel(relation);
    }

    public Link linkToProdutoFoto(Long restauranteId, Long produtoId) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class).recuperarFoto(restauranteId, produtoId)).withSelfRel();
    }

    public Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeController.class)
                .buscar(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String rel) {
        return linkTo(CidadeController.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstado(Long estadoId, String rel) {
        return linkTo(methodOn(EstadoController.class)
                .buscar(estadoId)).withRel(rel);
    }

    public Link linkToEstado(Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(String rel) {
        return linkTo(EstadoController.class).withRel(rel);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha(Long cozinhaId, String relation) {
        return linkTo(methodOn(CozinhaController.class).buscar(cozinhaId)).withRel(relation);
    }

    public Link linkToCozinha(Long cozinhaId) {
        return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstatisticas(String relation) {
        return linkTo(EstatisticasController.class).withRel(relation);
    }

    public Link linkToEstatisticasVendasDiarias(String relation) {
        var templateVariable = new TemplateVariables(
            new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
            new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
            new TemplateVariable("dataCriacaoFinal", VariableType.REQUEST_PARAM),
            new TemplateVariable("timeoffset", VariableType.REQUEST_PARAM));

        var estatiticas = linkTo(methodOn(EstatisticasController.class).consultarVendasDiarias(null, null)).toUri().toString();
        //var estatictica = linkTo(methodOn(EstatisticasController.class).consultarVendasDiarias(null, null)).toUri().toString();
        return Link.of(UriTemplate.of(estatiticas, templateVariable), relation);
    }
}
