package com.er7.er7foodapi.api.controller;


import com.er7.er7foodapi.api.assembler.PermissaoModelAssembler;
import com.er7.er7foodapi.api.model.PermissaoModel;
import com.er7.er7foodapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoID}/permissoes")
public class GrupoPermissaoController {

    @Autowired private PermissaoModelAssembler permissaoModelAssembler;
    @Autowired private CadastroGrupoService cadastroGrupoService;

    @GetMapping
    public List<PermissaoModel> permissoes(@PathVariable Long grupoID) {
        return permissaoModelAssembler.toColletionModel(cadastroGrupoService.permissoes(grupoID));
    }

    @PutMapping("/{permissaoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoID, @PathVariable Long permissaoID) {
        cadastroGrupoService.associar(grupoID, permissaoID);
    }

    @DeleteMapping("/{permissaoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoID, @PathVariable Long permissaoID) {
        cadastroGrupoService.desassociarPermissao(grupoID, permissaoID);
    }
}
