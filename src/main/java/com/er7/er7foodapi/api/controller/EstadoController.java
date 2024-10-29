package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.EstadoInputDisassembler;
import com.er7.er7foodapi.api.assembler.EstadoModelAssembler;
import com.er7.er7foodapi.api.model.EstadoModel;
import com.er7.er7foodapi.api.model.input.EstadoInput;
import com.er7.er7foodapi.api.openapi.controller.EstadoControllerOpenApi;
import com.er7.er7foodapi.domain.repository.EstadoRepository;
import com.er7.er7foodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired private EstadoRepository estadoRepository;
    @Autowired private CadastroEstadoService estadoService;
    @Autowired private EstadoModelAssembler estadoModelAssembler;
    @Autowired private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public CollectionModel<EstadoModel> listar() {
        return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
    }

    @GetMapping("/{estadoId}")
    public EstadoModel buscar(@PathVariable Long estadoId) {
        return estadoModelAssembler.toModel(estadoService.buscarOuFalhar(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        var estado = estadoInputDisassembler.toDomainObject(estadoInput);
        return estadoModelAssembler.toModel(estadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        var estadoDB = this.estadoService.buscarOuFalhar(estadoId);
        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoDB);
        return estadoModelAssembler.toModel(estadoService.salvar(estadoDB));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        this.estadoService.remover(estadoId);
    }
}
