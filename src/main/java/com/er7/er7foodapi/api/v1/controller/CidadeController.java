package com.er7.er7foodapi.api.v1.controller;

import com.er7.er7foodapi.api.ResourceUriHelper;
import com.er7.er7foodapi.api.v1.assembler.CidadeInputDisassembler;
import com.er7.er7foodapi.api.v1.assembler.CidadeModelAssembler;
import com.er7.er7foodapi.api.v1.model.CidadeModel;
import com.er7.er7foodapi.api.v1.model.input.CidadeInput;
import com.er7.er7foodapi.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.er7.er7foodapi.domain.exception.EstadoNaoEncontradoException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.repository.CidadeRepository;
import com.er7.er7foodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired private CidadeRepository cidadeRepository;
    @Autowired private CadastroCidadeService cidadeService;
    @Autowired private CidadeModelAssembler cidadeModelAssembler;
    @Autowired private CidadeInputDisassembler cidadeInputDisassembler;

    @Deprecated
    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeModel> listar() {
        var todasCidades = cidadeRepository.findAll();
         return cidadeModelAssembler.toCollectionModel(todasCidades);
    }

    @Deprecated
    @GetMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModel buscar(@PathVariable Long cidadeId) {
        return cidadeModelAssembler.toModel(cidadeService.buscarOuFalhar(cidadeId));
    }

    @Deprecated
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            var cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            var cidadeModel = cidadeModelAssembler.toModel(cidadeService.salvar(cidade));

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e ) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Deprecated
    @PutMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            var cidadeDB = this.cidadeService.buscarOuFalhar(cidadeId);
            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeDB);
            return cidadeModelAssembler.toModel(cidadeService.salvar(cidadeDB));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Deprecated
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        this.cidadeService.excluir(cidadeId);
    }

}
