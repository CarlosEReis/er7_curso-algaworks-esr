package com.er7.er7foodapi.api.v1.controller;

import com.er7.er7foodapi.api.v1.FoodLinks;
import com.er7.er7foodapi.api.v1.assembler.UsuarioModelAssembler;
import com.er7.er7foodapi.api.v1.model.UsuarioModel;
import com.er7.er7foodapi.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.er7.er7foodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes/{restauranteID}/responsaveis")
public class RestauranteResponsaveisController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired private CadastroRestauranteService restauranteService;
    @Autowired private UsuarioModelAssembler usuarioModelAssembler;
    @Autowired private FoodLinks foodLinks;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UsuarioModel> listarResponsaveis(@PathVariable Long restauranteID) {
        var responsaveis = restauranteService.buscarOuFalhar(restauranteID).getResponsaveis();
        var usuarioCollectionModel = usuarioModelAssembler.toCollectionModel(responsaveis)
            .removeLinks()
            .add(foodLinks.linkToResponsaveisRestaurante(restauranteID))
            .add(foodLinks.linkToResponsaveisRestauranteAssociar(restauranteID, "associar"));

        usuarioCollectionModel.getContent().forEach(
            usuarioModel -> usuarioModel.add(foodLinks.linkToResponsaveisRestauranteDesassociar(restauranteID, usuarioModel.getId(),"desassociar")));

        return usuarioCollectionModel;
    }

    @DeleteMapping(path = "/{usuarioID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociaResponsavel(@PathVariable Long restauranteID, @PathVariable Long usuarioID) {
        restauranteService.desassociarResponsavel(restauranteID, usuarioID);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{usuarioID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
        public ResponseEntity<Void> associoarResponsavel(@PathVariable Long restauranteID, @PathVariable Long usuarioID) {
        restauranteService.associarResponsavel(restauranteID, usuarioID);
        return ResponseEntity.noContent().build();
    }
}
