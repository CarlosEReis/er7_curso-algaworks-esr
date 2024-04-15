package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.CidadeInputDisassembler;
import com.er7.er7foodapi.api.assembler.CidadeModelAssembler;
import com.er7.er7foodapi.api.model.CidadeModel;
import com.er7.er7foodapi.api.model.input.CidadeInput;
import com.er7.er7foodapi.domain.exception.EstadoNaoEncontradoException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.repository.CidadeRepository;
import com.er7.er7foodapi.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired private CidadeRepository cidadeRepository;
    @Autowired private CadastroCidadeService cidadeService;
    @Autowired private CidadeModelAssembler cidadeModelAssembler;
    @Autowired private CidadeInputDisassembler cidadeInputDisassembler;

    @ApiOperation("Lista as cidades.")
    @GetMapping
    public List<CidadeModel> listar() {
        return cidadeModelAssembler.toColletionModel(cidadeRepository.findAll());
    }

    @ApiOperation("Busca uma cidade específica com base no ID.")
    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId) {
        return cidadeModelAssembler.toModel(cidadeService.buscarOuFalhar(cidadeId));
    }

    @ApiOperation("Cadastra uma cidade.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            var cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            return cidadeModelAssembler.toModel(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e ) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Atualiza uma cidade específica com base no ID.")
    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId, @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados.") @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            var cidadeDB = this.cidadeService.buscarOuFalhar(cidadeId);
            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeDB);
            return cidadeModelAssembler.toModel(cidadeService.salvar(cidadeDB));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Exclui uma cidade específica com base no ID.")
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@ApiParam(value = "ID de uma cidade") @PathVariable Long cidadeId) {
        this.cidadeService.excluir(cidadeId);
    }

}
