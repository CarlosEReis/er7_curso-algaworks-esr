package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.UsuarioModelAssembler;
import com.er7.er7foodapi.api.model.UsuarioModel;
import com.er7.er7foodapi.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.er7.er7foodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteID}/responsaveis")
public class RestauranteResponsaveisController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired private CadastroRestauranteService restauranteService;
    @Autowired private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UsuarioModel> listarResponsaveis(@PathVariable Long restauranteID) {
        var responsaveis = restauranteService.buscarOuFalhar(restauranteID).getResponsaveis();
        return usuarioModelAssembler.toCollectionModel(responsaveis);
    }

    @DeleteMapping(path = "/{usuarioID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociaResponsavel(@PathVariable Long restauranteID, @PathVariable Long usuarioID) {
        restauranteService.desassociarResponsavel(restauranteID, usuarioID);
    }

    @PutMapping(path = "/{usuarioID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associoarResponsavel(@PathVariable Long restauranteID, @PathVariable Long usuarioID) {
        restauranteService.associarResponsavel(restauranteID, usuarioID);
    }
}
