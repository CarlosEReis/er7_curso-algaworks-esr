package com.er7.er7foodapi.api.controller;


import com.er7.er7foodapi.api.FoodLinks;
import com.er7.er7foodapi.api.assembler.PermissaoModelAssembler;
import com.er7.er7foodapi.api.model.PermissaoModel;
import com.er7.er7foodapi.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.er7.er7foodapi.domain.model.Grupo;
import com.er7.er7foodapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired private PermissaoModelAssembler permissaoModelAssembler;
    @Autowired private CadastroGrupoService cadastroGrupoService;
    @Autowired private FoodLinks foodLinks;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        var permissaoModels = permissaoModelAssembler.toCollectionModel(grupo.getPermissoes())
                .add(foodLinks.linkToAssociarPermissao(grupoId));
        permissaoModels.getContent().forEach(
            permissaoModel -> permissaoModel.add(foodLinks.linkToDesassociarPermissao(grupoId, permissaoModel.getId())));
        return permissaoModels;
    }

    @Override
    @PutMapping("/{permissaoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoID) {
        cadastroGrupoService.associar(grupoId, permissaoID);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{permissaoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoID) {
        cadastroGrupoService.desassociarPermissao(grupoId, permissaoID);
        return ResponseEntity.noContent().build();
    }


}
