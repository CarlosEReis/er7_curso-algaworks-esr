package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.GrupoModelAssembler;
import com.er7.er7foodapi.api.model.GrupoModel;
import com.er7.er7foodapi.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioID}/grupos")
public class UsuarioGrupoController {

    @Autowired private CadastroUsuarioService usuarioService;
    @Autowired private GrupoModelAssembler grupoModelAssembler;

    @GetMapping
    public List<GrupoModel> listar(@PathVariable Long usuarioID) {
        return grupoModelAssembler.toCollectionModel(usuarioService.listarGrupos(usuarioID));
    }

    @DeleteMapping("/{grupoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassocionar(@PathVariable Long usuarioID, @PathVariable Long grupoID) {
        usuarioService.desassociaGrupo(usuarioID, grupoID);
    }

    @PutMapping("/{grupoID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associonar(@PathVariable Long usuarioID, @PathVariable Long grupoID) {
        usuarioService.associoaGrupo(usuarioID, grupoID);
    }

}
