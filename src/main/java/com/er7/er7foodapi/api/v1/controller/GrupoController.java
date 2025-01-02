package com.er7.er7foodapi.api.v1.controller;

import com.er7.er7foodapi.api.v1.assembler.GrupoInputDisassembler;
import com.er7.er7foodapi.api.v1.assembler.GrupoModelAssembler;
import com.er7.er7foodapi.api.v1.assembler.PermissaoModelAssembler;
import com.er7.er7foodapi.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.er7.er7foodapi.api.v1.model.GrupoModel;
import com.er7.er7foodapi.api.v1.model.input.GrupoInput;
import com.er7.er7foodapi.domain.model.Grupo;
import com.er7.er7foodapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired private GrupoModelAssembler grupoModelAssembler;
    @Autowired private CadastroGrupoService cadastroGrupoService;
    @Autowired private GrupoInputDisassembler grupoInputDisassembler;
    @Autowired private PermissaoModelAssembler permissaoModelAssembler;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel criar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
        return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupo));
    }

    @GetMapping
    public CollectionModel<GrupoModel> listar() {
        return grupoModelAssembler.toCollectionModel(cadastroGrupoService.listar());
    }

    @GetMapping(path = "/{grupoID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoModel buscar(@PathVariable Long grupoID) {
        return grupoModelAssembler.toModel(cadastroGrupoService.buscarOuFalhar(grupoID));
    }

    @PutMapping(path = "/{grupoID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoModel atualizar(@PathVariable Long grupoID, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoDB = cadastroGrupoService.buscarOuFalhar(grupoID);
        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoDB);
        return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupoDB));
    }

    @DeleteMapping("/{grupoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long grupoID) {
        cadastroGrupoService.excluir(grupoID);
    }

}
