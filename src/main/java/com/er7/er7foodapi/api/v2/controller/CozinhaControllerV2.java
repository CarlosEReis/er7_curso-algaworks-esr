package com.er7.er7foodapi.api.v2.controller;

import com.er7.er7foodapi.api.v1.controller.CozinhaController;
import com.er7.er7foodapi.api.v2.assembler.CozinhaInputDisassemblerV2;
import com.er7.er7foodapi.api.v2.assembler.CozinhaModelAssemblerV2;
import com.er7.er7foodapi.api.v2.model.CozinhaModelV2;
import com.er7.er7foodapi.api.v2.model.input.CozinhaInputV2;
import com.er7.er7foodapi.api.v2.openapi.controller.CozinhaControllerV2OpenApi;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import com.er7.er7foodapi.domain.service.CadastroCozinhaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/v2/cozinhas")
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(CozinhaController.class);

    @Autowired private CozinhaRepository cozinhaRepository;
    @Autowired private CadastroCozinhaService cadastroCozinha;
    @Autowired private CozinhaModelAssemblerV2 cozinhaModelAssembler;
    @Autowired private CozinhaInputDisassemblerV2 cozinhaInputDisassembler;
    @Autowired private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaModelV2> listar(@PageableDefault(size = 10) Pageable pageable) {
        LOGGER.info("Consultando Cozinhas com páginas de {} registros", pageable.getPageSize());

        Page<Cozinha> pageCozinhas = cozinhaRepository.findAll(pageable);
        PagedModel<CozinhaModelV2> cozinhasPagedModel = pagedResourcesAssembler.toModel(pageCozinhas, cozinhaModelAssembler);
        return cozinhasPagedModel;
    }

    @Override
    @GetMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModelV2 buscar(@PathVariable Long cozinhaId) {
        return cozinhaModelAssembler.toModel(cadastroCozinha.buscaOuFalha(cozinhaId));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelV2 adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
        var cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));
    }

    @Override
    @PutMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModelV2 atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputV2 cozinhaInput) {
        var cozinhzaDB = this.cadastroCozinha.buscaOuFalha(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhzaDB);
        return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhzaDB));
    }

    @Override
    @DeleteMapping(path = "/{cozinhaId}", produces = {})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        this.cadastroCozinha.excluir(cozinhaId);
    }
}

