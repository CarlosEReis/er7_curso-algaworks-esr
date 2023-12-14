package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.domain.exception.EntidadeEmUsoException;
import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import com.er7.er7foodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    public CadastroCozinhaService cadastroCozinha;

    @GetMapping
    public List<Cozinha> listar() {
        return this.cozinhaRepository.listar();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
        var cozinha = this.cozinhaRepository.buscar(cozinhaId);
        if ( cozinha != null )
            return ResponseEntity.ok(cozinha);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return this.cadastroCozinha.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public  ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        var cozinhzaDB = this.cozinhaRepository.buscar(cozinhaId);
        if ( cozinhzaDB == null )
            return ResponseEntity.notFound().build();
        BeanUtils.copyProperties(cozinha, cozinhzaDB, "id");
        this.cadastroCozinha.salvar(cozinhzaDB);
        return ResponseEntity.ok(cozinhzaDB);
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
        try {
            this.cadastroCozinha.excluir(cozinhaId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) { return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) { return ResponseEntity.status(HttpStatus.CONFLICT).build(); }

    }
}
