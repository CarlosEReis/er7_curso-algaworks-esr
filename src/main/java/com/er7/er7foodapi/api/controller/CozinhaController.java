package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import com.er7.er7foodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return this.cozinhaRepository.findAll();
    }

    @GetMapping("/{cozinhaId}")
    public Cozinha buscar(@PathVariable Long cozinhaId) {
        return this.cadastroCozinha.buscaOuFalha(cozinhaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return this.cadastroCozinha.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        var cozinhzaDB = this.cadastroCozinha.buscaOuFalha(cozinhaId);
        BeanUtils.copyProperties(cozinha, cozinhzaDB, "id");
        return this.cadastroCozinha.salvar(cozinhzaDB);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        this.cadastroCozinha.excluir(cozinhaId);
    }
}
