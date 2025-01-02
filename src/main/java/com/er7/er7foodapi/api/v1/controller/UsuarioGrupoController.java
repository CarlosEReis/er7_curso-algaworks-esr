package com.er7.er7foodapi.api.v1.controller;

import com.er7.er7foodapi.api.v1.FoodLinks;
import com.er7.er7foodapi.api.v1.assembler.GrupoModelAssembler;
import com.er7.er7foodapi.api.v1.model.GrupoModel;
import com.er7.er7foodapi.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.er7.er7foodapi.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios/{usuarioID}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    @Autowired private CadastroUsuarioService usuarioService;
    @Autowired private GrupoModelAssembler grupoModelAssembler;
    @Autowired private FoodLinks foodLinks;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioID) {
        var grupoCollectionModel = grupoModelAssembler
            .toCollectionModel(usuarioService.listarGrupos(usuarioID))
            .add(foodLinks.linkToAssociarGrupo(usuarioID));

        grupoCollectionModel.getContent().forEach(
            grupoModel -> grupoModel.add(foodLinks.linktoDesassociarGrupo(usuarioID, grupoModel.getId())));

        return grupoCollectionModel;
    }

    @Override
    @DeleteMapping("/{grupoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioID, @PathVariable Long grupoID) {
        usuarioService.desassociaGrupo(usuarioID, grupoID);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{grupoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long usuarioID, @PathVariable Long grupoID) {
        usuarioService.associoaGrupo(usuarioID, grupoID);
        return ResponseEntity.noContent().build();
    }

}
