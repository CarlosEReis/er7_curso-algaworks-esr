package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.domain.exception.EntidadeEmUsoException;
import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.model.Estado;
import com.er7.er7foodapi.domain.repository.EstadoRepository;
import com.er7.er7foodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService estadoService;

    @GetMapping
    public List<Estado> listar() {
        return this.estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public Estado buscar(@PathVariable Long estadoId) {
        return this.estadoService.buscarOuFalhar(estadoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado) {
        return this.estadoService.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public Estado atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
        var estadoDB = this.estadoService.buscarOuFalhar(estadoId);
        BeanUtils.copyProperties(estado, estadoDB, "id");
        return this.estadoService.salvar(estadoDB);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        this.estadoService.remover(estadoId);
    }
}
