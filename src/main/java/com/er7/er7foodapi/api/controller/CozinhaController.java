package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.model.CozinhasXmlWrapper;
import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping( produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXML() {
        return new CozinhasXmlWrapper(this.cozinhaRepository.listar());
    }

    @GetMapping("/{cozinhaId}")
    public Cozinha buscar(@PathVariable Long cozinhaId) {
        return this.cozinhaRepository.buscar(cozinhaId);
    }
}
