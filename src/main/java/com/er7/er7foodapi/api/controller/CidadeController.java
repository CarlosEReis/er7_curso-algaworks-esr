package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.exceptionhandler.Problema;
import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.exception.EstadoNaoEncontradoException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.model.Cidade;
import com.er7.er7foodapi.domain.repository.CidadeRepository;
import com.er7.er7foodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        try {
            return this.cidadeService.salvar(cidade);
        } catch (EstadoNaoEncontradoException e ) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        try {
            var cidadeDB = this.cidadeService.buscarOuFalhar(cidadeId);
            BeanUtils.copyProperties(cidade, cidadeDB, "id");
            return this.cidadeService.salvar(cidadeDB);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        this.cidadeService.excluir(cidadeId);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> trataEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
        var problema = Problema.builder()
                .datahora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> trataEntidadeNaoEncontradaException(NegocioException e) {
        var problema = Problema.builder()
                .datahora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(problema);
    }
}
