package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.domain.model.Cozinha;
import com.er7.er7foodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Cozinha> listar1() {
        System.out.println("PRODUCES >>>> JSON");
        return this.cozinhaRepository.listar();
    }

    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE })
    public List<Cozinha> listar2() {
        System.out.println("PRODUCES >>>> XML");
        return this.cozinhaRepository.listar();
    }
}
