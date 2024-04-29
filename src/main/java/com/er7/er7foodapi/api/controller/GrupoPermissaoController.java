package com.er7.er7foodapi.api.controller;


import com.er7.er7foodapi.api.assembler.PermissaoModelAssembler;
import com.er7.er7foodapi.api.model.PermissaoModel;
import com.er7.er7foodapi.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.er7.er7foodapi.domain.model.Grupo;
import com.er7.er7foodapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoID}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired private PermissaoModelAssembler permissaoModelAssembler;
    @Autowired private CadastroGrupoService cadastroGrupoService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        return permissaoModelAssembler.toColletionModel(grupo.getPermissoes());
    }

    @Override
    @PutMapping("/{permissaoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoID, @PathVariable Long permissaoID) {
        cadastroGrupoService.associar(grupoID, permissaoID);
    }

    @Override
    @DeleteMapping("/{permissaoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoID, @PathVariable Long permissaoID) {
        cadastroGrupoService.desassociarPermissao(grupoID, permissaoID);
    }


}
