package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.CidadeInputDisassembler;
import com.er7.er7foodapi.api.assembler.CidadeModelAssembler;
import com.er7.er7foodapi.api.model.CidadeModel;
import com.er7.er7foodapi.api.model.input.CidadeInput;
import com.er7.er7foodapi.domain.exception.EstadoNaoEncontradoException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.repository.CidadeRepository;
import com.er7.er7foodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired private CidadeRepository cidadeRepository;
    @Autowired private CadastroCidadeService cidadeService;
    @Autowired private CidadeModelAssembler cidadeModelAssembler;
    @Autowired private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeModel> listar() {
        return cidadeModelAssembler.toColletionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId) {
        return cidadeModelAssembler.toModel(cidadeService.buscarOuFalhar(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            var cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            return cidadeModelAssembler.toModel(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e ) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            var cidadeDB = this.cidadeService.buscarOuFalhar(cidadeId);
            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeDB);
            return cidadeModelAssembler.toModel(cidadeService.salvar(cidadeDB));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        this.cidadeService.excluir(cidadeId);
    }

}
