package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.repository.RestauranteRepository;
import com.er7.er7foodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return this.restauranteRepository.listar();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        var restaurante = this.restauranteRepository.buscar(restauranteId);
        if (restaurante == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(restaurante);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = restauranteService.adicionar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
        var restauranteDB = this.restauranteRepository.buscar(restauranteId);
        if (restauranteDB == null) return ResponseEntity.notFound().build();
        BeanUtils.copyProperties(restaurante, restauranteDB, "id");
        try {
            restauranteDB = this.restauranteService.adicionar(restauranteDB);
            return ResponseEntity.ok(restauranteDB);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
        var restaurante = this.restauranteRepository.buscar(restauranteId);
        if (restaurante == null) return ResponseEntity.notFound().build();
        merge(campos, restaurante);
        return this.atualizar(restauranteId, restaurante);
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteDestino) {
        campos.forEach((campo, valor) -> System.out.println(campo + " = " + valor) );
    }
}
