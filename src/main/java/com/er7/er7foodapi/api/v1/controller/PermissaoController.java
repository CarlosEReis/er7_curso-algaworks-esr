package com.er7.er7foodapi.api.v1.controller;

import com.er7.er7foodapi.api.v1.assembler.PermissaoModelAssembler;
import com.er7.er7foodapi.api.v1.model.PermissaoModel;
import com.er7.er7foodapi.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController {

    @Autowired private PermissaoRepository permissaoRepository;
    @Autowired private PermissaoModelAssembler permissaoModelAssembler;

    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        return permissaoModelAssembler.toCollectionModel(permissaoRepository.findAll());
    }

}
