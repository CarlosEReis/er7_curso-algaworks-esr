package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.domain.model.Cidade;
import com.er7.er7foodapi.domain.repository.CidadeRepository;
import com.er7.er7foodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return this.cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public Cidade buscar(@PathVariable Long cidadeId) {
        return this.cidadeService.buscarOuFalhar(cidadeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody Cidade cidade) {
        return this.cidadeService.salvar(cidade);
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        var cidadeDB = this.cidadeService.buscarOuFalhar(cidadeId);
        BeanUtils.copyProperties(cidade, cidadeDB, "id");
        return this.cidadeService.salvar(cidadeDB);
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        this.cidadeService.excluir(cidadeId);
    }
}
