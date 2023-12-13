package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.model.CozinhasXmlWrapper;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

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
        return this.cozinhaRepository.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public  ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        var cozinhzaDB = this.cozinhaRepository.buscar(cozinhaId);
        if ( cozinhzaDB == null )
            return ResponseEntity.notFound().build();
        BeanUtils.copyProperties(cozinha, cozinhzaDB, "id");
        this.cozinhaRepository.salvar(cozinhzaDB);
        return ResponseEntity.ok(cozinhzaDB);
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
        var cozinhaDB = this.cozinhaRepository.buscar(cozinhaId);
        if (cozinhaDB == null) return ResponseEntity.notFound().build();
        try {
            this.cozinhaRepository.remover(cozinhaDB);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }
}
