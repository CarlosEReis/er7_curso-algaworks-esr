package com.er7.er7foodapi.api.v1.controller;

import com.er7.er7foodapi.api.v1.FoodLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired private FoodLinks foodLinks;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(
            foodLinks.linkToCozinhas("cozinhas"),
            foodLinks.linkToPedidos("pedidos"),
            foodLinks.linkToRestaurantes("restaurantes"),
            foodLinks.linkToGrupos("grupos"),
            foodLinks.linkToUsuarios("usuarios"),
            foodLinks.linkToPermissoes("permissoes"),
            foodLinks.linkToFormasPagamento("formas-pagamento"),
            foodLinks.linkToEstados("estados"),
            foodLinks.linkToCidades("cidades"),
            foodLinks.linkToEstatisticas("estatisticas"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {}

}
