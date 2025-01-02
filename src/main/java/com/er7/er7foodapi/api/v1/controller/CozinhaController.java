package com.er7.er7foodapi.api.v1.controller;

import com.er7.er7foodapi.api.v1.assembler.CozinhaInputDisassembler;
import com.er7.er7foodapi.api.v1.assembler.CozinhaModelAssembler;
import com.er7.er7foodapi.api.v1.model.CozinhaModel;
import com.er7.er7foodapi.api.v1.model.input.CozinhaInput;
import com.er7.er7foodapi.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import com.er7.er7foodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired private CozinhaRepository cozinhaRepository;
    @Autowired private CadastroCozinhaService cadastroCozinha;
    @Autowired private CozinhaModelAssembler cozinhaModelAssembler;
    @Autowired private CozinhaInputDisassembler cozinhaInputDisassembler;
    @Autowired private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> pageCozinhas = cozinhaRepository.findAll(pageable);
        PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler.toModel(pageCozinhas, cozinhaModelAssembler);
        return cozinhasPagedModel;
    }

    @GetMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModel buscar(@PathVariable Long cozinhaId) {
        return cozinhaModelAssembler.toModel(cadastroCozinha.buscaOuFalha(cozinhaId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        var cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));
    }

    @PutMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
        var cozinhzaDB = this.cadastroCozinha.buscaOuFalha(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhzaDB);
        return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhzaDB));
    }

    @DeleteMapping(path = "/{cozinhaId}", produces = {})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        this.cadastroCozinha.excluir(cozinhaId);
    }
}
