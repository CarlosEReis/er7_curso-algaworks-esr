package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.CozinhaInputDisassembler;
import com.er7.er7foodapi.api.assembler.CozinhaModelAssembler;
import com.er7.er7foodapi.api.model.CozinhaModel;
import com.er7.er7foodapi.api.model.input.CozinhaInput;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import com.er7.er7foodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired private CozinhaRepository cozinhaRepository;
    @Autowired private CadastroCozinhaService cadastroCozinha;
    @Autowired private CozinhaModelAssembler cozinhaModelAssembler;
    @Autowired private CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public List<CozinhaModel> listar() {
        return cozinhaModelAssembler.toColletionModel(cozinhaRepository.findAll());
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscar(@PathVariable Long cozinhaId) {
        return cozinhaModelAssembler.toModel(cadastroCozinha.buscaOuFalha(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        var cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
        var cozinhzaDB = this.cadastroCozinha.buscaOuFalha(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhzaDB);
        return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhzaDB));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        this.cadastroCozinha.excluir(cozinhaId);
    }
}
